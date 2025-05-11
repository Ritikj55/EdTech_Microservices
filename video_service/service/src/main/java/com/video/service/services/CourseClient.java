package com.video.service.services;


import com.video.service.dto.CourseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="course-service" , url ="http://localhost:8086/courses")
public interface CourseClient {
    @GetMapping("/{id}")
    CourseDto getCourseById(@PathVariable(value="id") String id);
    @PostMapping("/{courseId}/video/{videoId}")
    CourseDto addVideoToCourse(@PathVariable(value="courseId")String courseId,@PathVariable(value="videoId") String videoId);
}
