package com.video.service.services;

import com.video.service.config.AppConstants;
import com.video.service.dto.CourseDto;
import com.video.service.dto.CustomPageResponse;
import com.video.service.dto.ResourceContentType;
import com.video.service.dto.VideoDto;
import com.video.service.entities.Video;
import com.video.service.exceptions.ResourceNotFoundException;
import com.video.service.repo.VideoRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoRepo videoRepo;
    @Autowired
    private ModelMapper modelMapper;

    private CourseClient courseClient;

    private  int count = 0;
//    @Autowired
//    private CourseRepo courseRepo ;

    Logger LOG = LoggerFactory.getLogger(VideoServiceImpl.class);
    @Autowired
    FileServiceImpl fileServiceImpl;

    VideoServiceImpl(VideoRepo videoRepo, ModelMapper modelMapper, CourseClient courseClient) {
        this.videoRepo = videoRepo;
        this.courseClient = courseClient;
        this.modelMapper = modelMapper;
    }


    @Override
    public VideoDto createVideo(MultipartFile videoFile) throws ResourceNotFoundException, IOException {
        Video video = new Video();
        String videoId = UUID.randomUUID().toString();
        video.setVideoId(videoId);
        String filePath = fileServiceImpl.save(videoFile, AppConstants.videoOutput, videoFile.getOriginalFilename());
        video.setFilePath(filePath);
        video.setTitle(videoFile.getOriginalFilename());
        video.setContentType(videoFile.getContentType());
        Video response = videoRepo.save(video);
        VideoDto responseVideoDto = modelMapper.map(response, VideoDto.class);
        CourseDto courseDto = getCourse(responseVideoDto.getCourseId());
        addVideoToCourse(responseVideoDto.getCourseId(), responseVideoDto.getVideoId());
        responseVideoDto.setCoursecontent(courseDto);
        return responseVideoDto;
    }


    @Override
    public VideoDto updateVideo(String videoId, VideoDto videoDto) throws ResourceNotFoundException {
        Video video = videoRepo.findById(videoId).get();
        if (video == null)
            throw new ResourceNotFoundException("Video with id " + videoId + " do not exist");
        Video updateVideo = modelMapper.map(videoDto, Video.class);
        video = updateVideo;
        Video response = videoRepo.save(video);
        VideoDto responseVideoDto = modelMapper.map(response, VideoDto.class);
        CourseDto courseDto = getCourse(responseVideoDto.getCourseId());
        addVideoToCourse(responseVideoDto.getCourseId(), responseVideoDto.getVideoId());
        responseVideoDto.setCoursecontent(courseDto);
        return responseVideoDto;
    }

    @Override
    public VideoDto getVideoById(String videoId) throws ResourceNotFoundException {
        Video video = videoRepo.findById(videoId).orElseThrow(() -> {
            try {
                throw new ResourceNotFoundException("Video not found ");
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        VideoDto videoDto = modelMapper.map(video, VideoDto.class);
        CourseDto courseDto = getCourse(videoDto.getCourseId());
        videoDto.setCoursecontent(courseDto);
        return videoDto;
    }

    @Override
    public CustomPageResponse<VideoDto> getAllVideos(int pageSize, int pageNumber, String sortBy, int order) {

        List<VideoDto> videoDtos;
        Sort sort = Sort.by(order == 1 ? Sort.Order.asc(sortBy) : Sort.Order.desc(sortBy));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Video> pages = videoRepo.findAll(pageable);
        List<Video> responses = pages.stream().toList();
        videoDtos = new ArrayList<>();
        for (Video video : responses) {
            VideoDto videoDto = modelMapper.map(video, VideoDto.class);
            CourseDto courseDto = getCourse(videoDto.getCourseId());
            videoDto.setCoursecontent(courseDto);
            videoDtos.add(videoDto);
        }
        CustomPageResponse<VideoDto> customPageResponse = new CustomPageResponse<>();
        customPageResponse.setTotalPages(pageSize);
        customPageResponse.setPageNumber(pageNumber);
        customPageResponse.setTotalElements(pages.getTotalElements());
        customPageResponse.setContent(videoDtos);
        customPageResponse.setLast(pages.isLast());
        return customPageResponse;
    }

    @Override
    public void deleteVideo(String videoId) throws ResourceNotFoundException {
        Video video = videoRepo.findById(videoId).orElseThrow(() -> new ResourceNotFoundException("Video with id " + videoId + " not found"));
        videoRepo.delete(video);
        System.out.println("Video with id " + videoId + " deleted");
    }

    @Override
    public List<VideoDto> searchVideos(String keyword) {
        return null;
    }

    @Override
    public ResourceContentType serveVideo(String videoId) {
        Video video = videoRepo.findById(videoId).orElseThrow(() -> {
            try {
                throw new ResourceNotFoundException("video with Id " + videoId + " not found");
            } catch (ResourceNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        String filePath = video.getFilePath();
        Resource resource = new FileSystemResource(filePath);
        ResourceContentType resourceContentType = new ResourceContentType();
        resourceContentType.setResource(resource);
        resourceContentType.setContentType(video.getContentType());
        return resourceContentType;
    }

    @Override
    public ResponseEntity<String> getInfo() throws ResourceNotFoundException {
        if(count < 3 ){
            LOG.info("Info count is " , count);
            count++ ;
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The count is less than 3");
        }
        LOG.info("************************");
        count = 0 ;
       return  ResponseEntity.ok("This is acknowledgement from video service ");

    }

    CourseDto getCourse(String courseId) {
        try {
            CourseDto courseDto = courseClient.getCourseById(courseId);
            return courseDto;
        } catch (Exception e) {
            LOG.error("Error while retriving course ");
            e.printStackTrace();
            return null;
        }
    }

    CourseDto addVideoToCourse(String courseId, String videoId) {
        try {
            CourseDto courseDto = courseClient.addVideoToCourse(courseId, videoId);
            return courseDto;
        } catch (Exception e) {
            LOG.error("Error occurred while makeing post call to course service for id {} ", courseId);
            e.printStackTrace();
            return null;
        }

    }
}