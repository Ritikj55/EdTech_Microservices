package com.video.service.controller;


import com.video.service.dto.CustomPageResponse;
import com.video.service.dto.ResourceContentType;
import com.video.service.dto.VideoDto;
import com.video.service.exceptions.ResourceNotFoundException;
import com.video.service.services.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.video.service.config.AppConstants;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {
    @Autowired
    VideoServiceImpl videoService;

    @GetMapping("/{id}")
    VideoDto getById(@PathVariable("id") String id) throws ResourceNotFoundException, ResourceNotFoundException {
        return videoService.getVideoById(id);
    }

    @GetMapping("/")
    CustomPageResponse<VideoDto> getAll(@RequestParam(value = "pagesize", required = false, defaultValue = AppConstants.PAGE_SIZE) int pageSize,
                                        @RequestParam(value = "pagenumber", required = false, defaultValue = AppConstants.NUMBEROFPAGES) int pageNumber,
                                        @RequestParam(value = "sortby", required = false, defaultValue = AppConstants.SORT_BY) String sortBy,
                                        @RequestParam(value = "order", required = false, defaultValue = AppConstants.order) int order) {
        return videoService.getAllVideos(pageSize,pageNumber,sortBy,order);
    }
    @PostMapping("/")
    VideoDto create(@RequestParam("video") MultipartFile videoFile) throws ResourceNotFoundException, IOException {
        return  videoService.createVideo(videoFile);
    }
    @GetMapping("/{videoId}/serveVideo")
    ResponseEntity<Resource> serveVideo(@PathVariable(value="videoId") String videoId){
        ResourceContentType resourceContentType = videoService.serveVideo(videoId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(resourceContentType.getContentType())).body(resourceContentType.getResource());
    }
    @DeleteMapping("/{videoId}")
    ResponseEntity<String> deleteVideo(@PathVariable(value="videoId") String videoId) throws ResourceNotFoundException {
        videoService.deleteVideo(videoId);
        return new ResponseEntity<>("Video deleted successfully",HttpStatus.ACCEPTED);
    }
    @PutMapping("/{videoId}")
    VideoDto updateVideo(@PathVariable(value="videoId")String videoId , @RequestBody VideoDto videoDto) throws ResourceNotFoundException {
       return  videoService.updateVideo(videoId,videoDto);
    }
    @GetMapping("/info")
    ResponseEntity<String> getInfo() throws ResourceNotFoundException {
        return  videoService.getInfo();
    }

}
