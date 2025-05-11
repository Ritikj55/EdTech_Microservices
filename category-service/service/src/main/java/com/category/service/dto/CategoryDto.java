package com.category.service.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CategoryDto {


    private String id;
    @Size(min=3,max = 50 , message = "Length of description is not valid")
    private String desc ;
    private  String title ;
    private Date addedDate ;
    private List<String> coursesId;

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setDesc(String desc) {
//        this.desc = desc;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public void setAddedDate(Date addedDate) {
//        this.addedDate = addedDate;
//    }
//
//    public void setCourseId(List<String> courseId) {
//        this.courseId = courseId;
//    }
//
//    public String getDesc() {
//        return desc;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public Date getAddedDate() {
//        return addedDate;
//    }
//
//    public List<String> getCourseId() {
//        return courseId;
//    }
//
//    @Override
//    public String toString() {
//        return "CategoryDto{" +
//                "id='" + id + '\'' +
//                ", desc='" + desc + '\'' +
//                ", title='" + title + '\'' +
//                ", addedDate=" + addedDate +
//                ", courseId=" + courseId +
//                '}';
//    }



}
