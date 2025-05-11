package com.video.service.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VideoDto {
    private String videoId;
    private String title;
    private String filePath ;
    private String contentType ;
    private String courseId ;
    private CourseDto coursecontent ;
}
