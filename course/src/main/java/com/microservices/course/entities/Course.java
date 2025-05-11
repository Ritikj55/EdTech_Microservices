package com.microservices.course.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "courses")



public class Course {
    @Id
    private String id ;

    private String title ;
    private String bannerContentType ;
    private String shortDescription ;
    private String longDescription ;
    private String banner ;
    private Date createdDated ;
    private boolean  live ;
    private Double price ;
    private  List<String> categoryId ;
    private  List<String> videoIds;

}
