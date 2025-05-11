package com.microservices.course.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter


public class VideoDto {
    private String videoId;
    private String title;
    private String filePath ;
    private String contentType ;
    private  String courseId ;
}
