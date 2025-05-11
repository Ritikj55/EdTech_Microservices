package com.microservices.course.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CourseDto {
    private  String Id;
    private String title ;
    private String bannerContentType ;
    private String shortDescription ;
    private String longDescription ;
    private String banner ;
    private Date createdDated ;
    private boolean  live ;
    private Double price ;
    private  List<String> categoryId ;
    private  List<String> videoIds ;
}
