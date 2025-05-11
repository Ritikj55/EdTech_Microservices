package com.category.service.services;

import com.category.service.config.AppConstants;
import com.category.service.dto.CategoryDto;
import com.category.service.dto.CourseDto;
import com.category.service.dto.CustomPageResponse;
import com.category.service.entities.Category;
import com.category.service.exceptions.ResourceNotFoundException;
import com.category.service.repo.CategoryRepo;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService{
    Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
//    public CategoryServiceImpl(CategoryRepo categoryRepo, ModelMapper modelMapper) {
//        this.categoryRepo = categoryRepo;
//        this.modelMapper = modelMapper;
//    }

    @Autowired
    CategoryRepo categoryRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    RestTemplate restTemplate ;

    private static final Logger LOG = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String id = UUID.randomUUID().toString();
        Date date = new Date();
        categoryDto.setId(id);
        categoryDto.setAddedDate(date);
        if(modelMapper==null)
            System.out.println("Model mapper is null");
        Category request = modelMapper.map(categoryDto, Category.class);
        System.out.println("CategoryDto "+categoryDto.toString());
        System.out.println("The request category body "+request.toString());
        Category response = categoryRepo.save(request);
        System.out.println("Response "+response.toString());
        return  modelMapper.map(response,CategoryDto.class);
    }

    @Override
    public CustomPageResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy) {
        Sort sort = Sort.by(sortBy).descending();
        Pageable pageable =  PageRequest.of(pageNumber,pageSize,sort);
        List<Category> responses ;
        Page<Category> pages=  categoryRepo.findAll(pageable);
        responses = pages.stream().toList();
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for(Category response : responses){
            categoryDtoList.add(modelMapper.map(response,CategoryDto.class));
        }
        CustomPageResponse<CategoryDto> customPageResponse= new CustomPageResponse<>();
        customPageResponse.setContent(categoryDtoList);
        customPageResponse.setPageNumber(pageNumber);
        customPageResponse.setPageSize(pageSize);
        customPageResponse.setLast(pages.isLast());
        customPageResponse.setTotalElements(pages.getTotalElements());
        customPageResponse.setTotalPages(pages.getTotalPages());
        return customPageResponse;
    }

    @Override
    public CategoryDto get(String id) {
        Category category= categoryRepo.findById(id).get();
        CategoryDto categoryDto = modelMapper.map(category,CategoryDto.class);
        return  categoryDto ;
    }

    @Override
    public CategoryDto update(String id, CategoryDto newRequestBody) {
        Category currentCategory = categoryRepo.findById(id).get();
        Category newCategory = modelMapper.map(newRequestBody,Category.class);
        currentCategory = newCategory;
        currentCategory  =  categoryRepo.save(currentCategory);
        return modelMapper.map(currentCategory,CategoryDto.class);}

    @Override
    public void delete(String id) {
        Category category = categoryRepo.findById(id).get();
        categoryRepo.delete(category);
    }

//    @Override
//    public CategoryDto addCourseToCategory(String catId, String course) throws ResourceNotFoundException {
//        return null;
//    }

    @Override
    public CategoryDto addCourseToCategory(String catId, String courseId) throws ResourceNotFoundException {
//        Course course = courseRepo.findById(courseId).orElseThrow(()->
//                new ResourceNotFoundException("No Course was found")
//        );
        Category category =  categoryRepo.findById(catId).orElseThrow(()->
                new ResourceNotFoundException("No Category with the id was found")
        );
        CourseDto courseDto = getCourse(courseId);
        if(courseDto == null)
            throw new ResourceNotFoundException("Course with id  "+courseId +" not found");
        List<String> courseCatogoriesId = courseDto.getCategoryId() !=null ? courseDto.getCategoryId():new ArrayList<String>();
        courseCatogoriesId.add(catId);
        courseDto.setCategoryId(courseCatogoriesId);
        List<String> courses =  category.getCoursesId()==null?new ArrayList<String>():category.getCoursesId();
        updateCourse(courseDto);
        courses.add(courseId);
        category.setCoursesId(courses);
        categoryRepo.save(category);
        Category response = categoryRepo.findById(catId).get();
        return modelMapper.map(response,CategoryDto.class);
    }

    @Override
    public List<CourseDto> getAllCourses(String categoryId) throws ResourceNotFoundException {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category with id "+categoryId +" not found"));
        List<String> courses = category.getCoursesId() !=null ? category.getCoursesId():new ArrayList<String>();
        if(courses.isEmpty())
            return  new ArrayList<>();
        List<CourseDto> courseDtos = new ArrayList<CourseDto>();
        for(String courseId : courses){
            courseDtos.add(getCourse(courseId));
        }
        return courseDtos;
    }

    public  void updateCourse(CourseDto courseDto){
        try {
            HttpEntity<CourseDto> courseDtoHttpEntity = new HttpEntity<>(courseDto);
            ResponseEntity<CourseDto> response = restTemplate.exchange(AppConstants.courseUri+courseDto.getId(), HttpMethod.PUT, courseDtoHttpEntity, CourseDto.class, "");
            LOG.info("Get course updated , with response as ,",response.getBody() );
        }
        catch (RestClientException restClientException){
            LOG.error("Save failed for course with id {} the error is ",courseDto.getId());
            restClientException.printStackTrace();
        }
    }

    public CourseDto getCourse(String courseId){
        try {
            ResponseEntity<CourseDto> response = restTemplate.exchange(AppConstants.courseUri + courseId, HttpMethod.GET, null, CourseDto.class);
            LOG.info("The Response from course service ",response.toString());
            return response.getBody();
        }
        catch (RestClientException exception){
            LOG.error("Getting a course failed with error  ");
            exception.printStackTrace() ;
             return  null;
        }
        }

//
//    @Override
//    public List<CourseDto> getCoursesOfCat(String categoryId) throws ResourceNotFoundException {
//        List<CourseDto> courseDtos ;
//        courseDtos = new ArrayList<>();
//        Category  category = categoryRepo.findById(categoryId).orElseThrow(()->
//                new ResourceNotFoundException("Category wth" +" id "+categoryId+" not found "));
//        List<String> courseIdList = category.getCourseId();
//        for(String courseId : courseIdList){
//            try {
//                Course course = courseRepo.findById(courseId).get();
//                courseDtos.add(modelMapper.map(course,CourseDto.class));
//            }
//            catch (Exception e){
//                log.info("Course with Id {} not found" ,courseId);
//            }
//
//        }
//        return  courseDtos;
//    }

}
