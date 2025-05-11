package com.microservices.course.dtos;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class CategoryDto {
    private String id;
    @Size(min=3,max = 50 , message = "Length of description is not valid")
    private String desc ;
    private  String title ;
    private Date addedDate ;
    private List<String> coursesId;
}
