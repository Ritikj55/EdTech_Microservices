package com.video.service.dto;

import lombok.*;
import org.springframework.core.io.Resource;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResourceContentType {


   private Resource resource;
   private String contentType ;
}
