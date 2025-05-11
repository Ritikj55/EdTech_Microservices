package com.video.service.services;

import com.video.service.dto.CustomPageResponse;
import com.video.service.dto.ResourceContentType;
import com.video.service.dto.VideoDto;
import com.video.service.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface VideoService {
    VideoDto createVideo(MultipartFile file) throws ResourceNotFoundException, IOException;
    VideoDto updateVideo(String videoId, VideoDto videoDto) throws ResourceNotFoundException;
    VideoDto getVideoById(String videoId) throws  ResourceNotFoundException;
    CustomPageResponse<VideoDto> getAllVideos(int  pageSize , int pageNumber , String orderBy , int order);
    void deleteVideo(String videoId) throws ResourceNotFoundException;
    List<VideoDto> searchVideos(String keyword);

    ResourceContentType serveVideo(String videoId);

    ResponseEntity<String> getInfo() throws ResourceNotFoundException ;
}
