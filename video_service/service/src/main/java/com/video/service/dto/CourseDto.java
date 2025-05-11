package com.video.service.dto;

import com.mongodb.annotations.Sealed;
import lombok.*;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
//    private  List<String> videoIds ;
}
