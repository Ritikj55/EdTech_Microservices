package com.microservices.course.controller;


import com.microservices.course.dtos.*;
import com.microservices.course.exceptionHandler.ResourceNotFoundException;
import com.microservices.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping("/")
    public CourseDto create(@RequestBody CourseDto courseDto){
        return  courseService.create(courseDto);
    }
    @PutMapping("/{id}")
    CourseDto update(@PathVariable("id") String id , @RequestBody CourseDto courseDto) throws ResourceNotFoundException {
        return  courseService.update(id,courseDto);
    }
    @GetMapping("/{id}")
    CourseDto getById(@PathVariable("id") String id ) throws ResourceNotFoundException {
        return courseService.getCourse(id);
    }
    @GetMapping("/")
    List<CourseDto> getAll(){
        System.out.println("Request received for getting all courses ");
        return courseService.getAllCourses();
    }
    @GetMapping("/contains")
    List<CourseDto> findByKeyword(@RequestParam(value = "keyword") String keyword ) throws ResourceNotFoundException {
        return courseService.searchCourses(keyword);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteById(@PathVariable("id") String id) throws ResourceNotFoundException {
        courseService.deleteCourse(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Course with id " +id+" is deleted");
    }
    @PostMapping("/{courseId}/banners")
    ResponseEntity<?> addBanner(@PathVariable("courseId") String courseId , @RequestParam(value="banner")MultipartFile multipartFile) throws IOException, ResourceNotFoundException {
        String contentType ;
        contentType = multipartFile.getContentType();
        if(contentType == null)
            contentType = "image/png";
        else if(!contentType.equals("image/png") && !contentType.equals("image/jpeg")){
            CustomMessage customMessage = new CustomMessage("Invalid content type",false);
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customMessage);
        }
        CourseDto courseDto = courseService.saveBanner(multipartFile,courseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDto);
    }
    @GetMapping("{courseId}/banners")
    ResourceContentType getBanner(@PathVariable("courseId") String courseId){
        return courseService.getBanner(courseId);
    }
    @GetMapping("/{id}/categories/")
    List<CategoryDto> getAllCatogories(@PathVariable(value="id") String courseId) throws ResourceNotFoundException {
        return  courseService.getAllCategories(courseId);
    }

    @PostMapping("/{courseId}/video/{videoId}")
    CourseDto addVideo(@PathVariable(value="courseId")String courseId , @PathVariable(value = "videoId")String videoId) throws ResourceNotFoundException{
        return courseService.addVideo(courseId,videoId);
    }
    @GetMapping("/{courseId}/videos/")
    List<VideoDto> getAllVideos(@PathVariable(value="courseId") String courseId) throws ResourceNotFoundException {
        return courseService.getAllVideos(courseId);
    }




}
