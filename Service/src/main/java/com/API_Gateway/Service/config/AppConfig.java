package com.API_Gateway.Service.config;


import org.springframework.cloud.gateway.filter.factory.RetryGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;

import java.time.Duration;

@Configuration
public class AppConfig {

@Bean
   public KeyResolver keyResolver(){
    return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
}
@Bean
public RedisRateLimiter rateLimiter(){
    return new RedisRateLimiter(0,10,1);
}

@Bean
RouteLocator customRouteLocator(RouteLocatorBuilder builder){
    return builder.routes()
            .route("course-service", r -> r.path("/courses/**")
                    .filters(
                            f->f.rewritePath("/courses/?(?<remaining>.*)","/api/v1/courses/${remaining}")
                                    .retry(
                                            config -> config.setRetries(5).
                                                    setMethods(HttpMethod.GET).
                                                    setBackoff(
                                                            Duration.ofMillis(1000) ,
                                                            Duration.ofMillis(5000),
                                                            2 ,
                                                            true
                                                    )
                                    )
                                    .circuitBreaker(c->c.setName("course-service-breaker").setFallbackUri("forward:/fallback/course-service"))
                    )
                    .uri("lb://COURSE-SERVICE"))
            .route("video-service", r -> r.path("/videos/**")
                    .filters(
                            f->f.rewritePath("/videos/?(?<remaining>.*)","/api/v1/videos/${remaining}")
                                    .retry(
                                                    config -> config.setRetries(5).
                                                            setMethods(HttpMethod.GET).
                                                            setBackoff(
                                                                    Duration.ofMillis(1000) ,
                                                                    Duration.ofMillis(5000),
                                                                    2 ,
                                                                    true
                                                            )
                                    )
                                    .circuitBreaker(c->c.setName("video-service-breaker").setFallbackUri("forward:/fallback/videos-service")
                                    ).
                                    requestRateLimiter(rateLimiter ->
                                            rateLimiter.setKeyResolver(keyResolver()).setRateLimiter(rateLimiter())
                                            )
                    )

                    .uri("lb://VIDEO-SERVICE"))
            .route("category-service", r -> r.path("/categories/**")
            .filters(
                    f->f.rewritePath("/categories/?(?<segment>.*)","/api/v1/categories/${segment}")
                            .retry(
                                    config -> config.setRetries(5).
                                            setMethods(HttpMethod.GET).
                                            setBackoff(
                                                    Duration.ofMillis(1000) ,
                                                    Duration.ofMillis(5000),
                                                    2 ,
                                                    true
                                            )
                            )
                            .circuitBreaker(c->c.setName("category-service-breaker").setFallbackUri("forward:/fallback/category-service"))
            )
            .uri("lb://CATEGORY-SERVICE"))
            .build();

}
}
