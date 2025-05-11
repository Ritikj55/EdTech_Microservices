package com.category.service.services;


import com.category.service.dto.CategoryDto;
import com.category.service.dto.CourseDto;
import com.category.service.dto.CustomPageResponse;
import com.category.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface CategoryService {
    CategoryDto create(CategoryDto categoryDto);
    CustomPageResponse<CategoryDto> getAll(int pageNumber , int pageSize , String sortBy);
    CategoryDto  get(String id) ;
    CategoryDto update(String id , CategoryDto  newRequestBody);
    void delete(String id);
    public CategoryDto addCourseToCategory(String catId, String course) throws ResourceNotFoundException;
    //List<CourseDto> getCoursesOfCat(String categoryId) throws ResourceNotFoundException;
    public List<CourseDto> getAllCourses(String categoryId) throws ResourceNotFoundException;
}
