package com.microservices.course.dtos;


import lombok.*;
import org.springframework.core.io.Resource;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResourceContentType {

    Resource resource ;
String resourceContentType ;


}
