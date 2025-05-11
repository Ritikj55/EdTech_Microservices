package com.category.service.controller;


import com.category.service.config.AppConstants;
import com.category.service.dto.CategoryDto;
import com.category.service.dto.CourseDto;
import com.category.service.dto.CustomPageResponse;
import com.category.service.exceptions.ResourceNotFoundException;
import com.category.service.repo.CategoryRepo;
import com.category.service.services.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    CategoryRepo categoryRepo ;
    @Autowired
    ModelMapper modelMapper ;
    @Autowired
    CategoryServiceImpl categoryServiceImpl;

    @PostMapping("/")
    CategoryDto create(@RequestBody @Valid CategoryDto categoryDto){
        return categoryServiceImpl.create(categoryDto);
    }
    @GetMapping("/")
    CustomPageResponse<CategoryDto> getAll(@RequestParam(value = "pageSize" , required = false , defaultValue=AppConstants.PAGE_SIZE) int pagesize,
                                           @RequestParam(value = "pageNumber", required = false , defaultValue = AppConstants.NUMBEROFPAGES) int pageNumber,
                                           @RequestParam(value = "sortBy" , required = false , defaultValue = AppConstants.SORT_BY) String sortBy){

        return  categoryServiceImpl.getAll(pageNumber,pagesize,sortBy);
    }
    @GetMapping("/{id}")
    CategoryDto getById(@PathVariable(value = "id") String id){
        return categoryServiceImpl.get(id);
    }
    @PutMapping("/{id}")
    CategoryDto updateById(@PathVariable(value="id") String id , @RequestBody @Valid CategoryDto categoryDto){
        return categoryServiceImpl.update(id,categoryDto);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteById(@PathVariable(value="id") String id){
        categoryServiceImpl.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Course deleted successfully");
    }
    @PostMapping("/{catId}/courses/{courseId}")
    CategoryDto addCourse(@PathVariable(value="catId") String categoryId , @PathVariable(value = "courseId" )String courseId) throws ResourceNotFoundException {
      CategoryDto categoryDto = categoryServiceImpl.addCourseToCategory(categoryId,courseId);
     return  categoryDto ;
    }
    @GetMapping("/{id}/courses/")
    List<CourseDto> getAllCourses(@PathVariable(value = "id") String catogoryId) throws ResourceNotFoundException {
        List<CourseDto> courseDtos = categoryServiceImpl.getAllCourses(catogoryId);
        return  courseDtos;
    }


}
