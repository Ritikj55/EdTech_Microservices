package com.category.service.dto;

import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    private List<String> categoryId ;
}
