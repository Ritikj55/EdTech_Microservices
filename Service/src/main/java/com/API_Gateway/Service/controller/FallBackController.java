package com.API_Gateway.Service.controller;

import com.API_Gateway.Service.dtos.FallbackDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class FallBackController {
   @RequestMapping("/fallback/category-service")
    Mono<FallbackDTO> categoryServiceFallBackUri(){
       return  Mono.just(new FallbackDTO("Category Service Failed !!",false));
   }
    @RequestMapping("/fallback/course-service")
    Mono<FallbackDTO> courseServiceFallBackUri(){
        return  Mono.just(new FallbackDTO("Category Service Failed !!",false));
    }
    @RequestMapping("/fallback/video-service")
    Mono<FallbackDTO> videoServiceFallBackUri(){
        return  Mono.just(new FallbackDTO("Category Service Failed !!",false));
    }
}
