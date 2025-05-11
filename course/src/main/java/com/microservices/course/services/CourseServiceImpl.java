package com.microservices.course.services;

import com.microservices.course.config.AppConstants;
import com.microservices.course.dtos.CategoryDto;
import com.microservices.course.dtos.CourseDto;
import com.microservices.course.dtos.ResourceContentType;
import com.microservices.course.dtos.VideoDto;
import com.microservices.course.entities.Course;
import com.microservices.course.exceptionHandler.ResourceNotFoundException;
import com.microservices.course.repo.CourseRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CourseServiceImpl implements  CourseService{
@Autowired
CourseRepo courseRepo;
@Autowired
ModelMapper modelMapper;

@Autowired
FileService fileService ;

@Autowired
    RestTemplate restTemplate;

@Autowired
    WebClient.Builder webClientBuilder ;

public static  Logger LOG = LoggerFactory.getLogger(CourseServiceImpl.class);
public CourseDto create(CourseDto courseDto){
    Course course = modelMapper.map(courseDto, Course.class);
    course.setId(UUID.randomUUID().toString());
    Course responseCourse = courseRepo.save(course);
    return  modelMapper.map(responseCourse,CourseDto.class);
}




    @Override
    public CourseDto update(String id, CourseDto courseDto) throws ResourceNotFoundException {
         Course requestBody = modelMapper.map(courseDto,Course.class);
         Course course = courseRepo.findById(id).orElseThrow(()->
            new ResourceNotFoundException("Course with this id" +id+" not found"));
         course = requestBody ;
         Course response = courseRepo.save(course);
         return modelMapper.map(response,CourseDto.class);
    }

    @Override
    public CourseDto getCourse(String id) throws ResourceNotFoundException {
      Course course = courseRepo.findById(id).orElseThrow(()->
          new ResourceNotFoundException("The course with the Id is is not found"));
      CourseDto courseDto = modelMapper.map(course,CourseDto.class);
      return courseDto;
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepo.findAll();
        List<CourseDto> courseDtos = new ArrayList<>();
        for(Course course : courses){
            courseDtos.add(modelMapper.map(course,CourseDto.class));
        }
        return courseDtos;
    }

    @Override
    public List<CourseDto> searchCourses(String keyword) throws ResourceNotFoundException {
        List<Course> courses = courseRepo.findByTitleContainingIgnoreCase(keyword);
        if(courses == null)
            throw  new ResourceNotFoundException("Keyword did not match any title");
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courses){
            courseDtos.add(modelMapper.map(course,CourseDto.class));
        }
        return courseDtos;
}

    @Override
    public CourseDto saveBanner(MultipartFile file, String courseId) throws IOException, ResourceNotFoundException {
        Course course = courseRepo.findById(courseId).orElseThrow(()->
            new ResourceNotFoundException("Course with Id not found"));
        String path = fileService.save(file,file.getOriginalFilename(), AppConstants.COURSE_BANNER_OUTPUT);
        course.setBanner(path);
        course.setBannerContentType(file.getContentType());
        Course responseCourse = courseRepo.save(course);
        return modelMapper.map(responseCourse,CourseDto.class);
}

    @Override
    public ResourceContentType getBanner(String courseId) {
         Course course = courseRepo.findById(courseId).get();
         ResourceContentType resourceContentType = new ResourceContentType();
         Resource resource  = new FileSystemResource(course.getBanner());
         resourceContentType.setResource(resource);
         resourceContentType.setResourceContentType(course.getBannerContentType());
         return resourceContentType;
    }

    @Override
    public String deleteCourse(String id) throws ResourceNotFoundException {
        Course course = courseRepo.findById(id).orElseThrow(()->
          new ResourceNotFoundException("Course with the Id is not found")
        );
        courseRepo.delete(course);
        return  id ;
}

    @Override
    public List<CategoryDto> getAllCategories(String courseId) throws ResourceNotFoundException {
        Course course = courseRepo.findById(courseId).orElseThrow(()->
                new ResourceNotFoundException("Course with Id"+ courseId +" not found"));
        List<String> categories = course.getCategoryId();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        if(categories == null)
            return categoryDtos;
        for(String categoryId : categories){
            categoryDtos.add(getCatogory(categoryId));
        }
        return  categoryDtos;
    }

    @Override
    public CourseDto addVideo(String courseId, String videoId) throws ResourceNotFoundException {
       Course course = courseRepo.findById(courseId).orElseThrow(()->
               new ResourceNotFoundException("Course with id "+courseId+" do not exist "));
       VideoDto videoDto = getVideo(videoId);
       if(videoDto == null )
           throw new ResourceNotFoundException("Video with id "+videoId+" not found");
       List<String> videoIds = course.getVideoIds()==null?new ArrayList<>():course.getVideoIds();
       videoIds.add(videoId);
       videoDto.setCourseId(courseId);
       updateVideo(videoDto);
       course.setVideoIds(videoIds);
       Course response  = courseRepo.save(course);
       return  modelMapper.map(response,CourseDto.class);
}

    @Override
    public List<VideoDto> getAllVideos(String courseId) throws ResourceNotFoundException {
        Course course = courseRepo.findById(courseId).orElseThrow(()->
                new ResourceNotFoundException("course with Id "+courseId +" now found")
                );
        List<String> videoIds = course.getVideoIds();
        List<VideoDto> videoDtos = new ArrayList<VideoDto>();
        if(videoIds == null || videoIds.isEmpty())
           return  videoDtos;
        for(String videoId : videoIds){
            videoDtos.add(getVideo(videoId));
        }
        return videoDtos;
    }

    public void updateVideo(VideoDto videoDto){
    try {
    VideoDto response =    webClientBuilder.build()
                .put().
                uri(AppConstants.VIDEO_URL + videoDto.getVideoId())
                .bodyValue(videoDto)
                .retrieve()
                .bodyToMono(VideoDto.class).
                block();
        LOG.info("Response of updated video is {} ",videoDto);
    }catch (Exception e){
        LOG.error("Error came while updating video with id  {}  ",videoDto.getVideoId());
    }
  }

    VideoDto getVideo(String videoId) {
        try {
            VideoDto videoDto = webClientBuilder.build().
                    get().
                    uri(AppConstants.VIDEO_URL + videoId).
                    retrieve().
                    bodyToMono(VideoDto.class).
                    block();
            return  videoDto;
        }catch (Exception e){
            LOG.error("Error occured while retriving videoDto for id ,{}",videoId);
            e.printStackTrace();
          return  null;
        }

    }


    CategoryDto getCatogory(String categoryId) {

        try {
            ResponseEntity<CategoryDto> exchange = restTemplate.exchange(AppConstants.CATEGORY_URL + categoryId, HttpMethod.GET, null, CategoryDto.class);
            return exchange.getBody();
        } catch (RestClientException exception) {
            LOG.error("Exception occured while retriving category ");
            exception.printStackTrace();
            return null;
        }
    }


}
