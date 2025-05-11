package com.microservices.course.services;


import com.microservices.course.dtos.CategoryDto;
import com.microservices.course.dtos.CourseDto;
import com.microservices.course.dtos.ResourceContentType;
import com.microservices.course.dtos.VideoDto;
import com.microservices.course.exceptionHandler.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {


    public CourseDto create(CourseDto courseDto) ;

    public CourseDto update(String id,CourseDto courseDto) throws ResourceNotFoundException;

    public CourseDto getCourse(String id) throws ResourceNotFoundException;

    public List<CourseDto> getAllCourses();

   // public void deleteCourse(String id) throws ResourceNotFoundException;
    public List<CourseDto> searchCourses(String keyword) throws ResourceNotFoundException;

    //public CourseDto addVideoToCourse(String courseId , String videoId) throws ResourceNotFoundException;

    //public List<VideoDto> getAllVideoOfCourse(String courseId) throws ResourceNotFoundException;

    public CourseDto saveBanner(MultipartFile file, String courseId) throws IOException, ResourceNotFoundException;

    public ResourceContentType getBanner(String courseId);

    public String deleteCourse(String id) throws ResourceNotFoundException;

    List<CategoryDto> getAllCategories(String courseId) throws  ResourceNotFoundException;

    public  CourseDto  addVideo(String courseId , String VideoId) throws  ResourceNotFoundException ;

    public List<VideoDto> getAllVideos(String courseId ) throws ResourceNotFoundException ;
}