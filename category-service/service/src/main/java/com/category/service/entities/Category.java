package com.category.service.entities;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Document(collection = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category {
    @Id
    private String id ;
    @Field("description")
    @NotNull(message = "Decsription cannot be null")
    private String desc ;
    @NotNull(message = "Title cannot be null ")
    private  String title ;
    private Date addedDate ;
    private  List<String> coursesId;

//    @Override
//    public String toString() {
//        return "Category{" +
//                "id='" + id + '\'' +
//                ", desc='" + desc + '\'' +
//                ", title='" + title + '\'' +
//                ", addedDate=" + addedDate +
//                ", courseId=" + courseId +
//                '}';
//    }
}
