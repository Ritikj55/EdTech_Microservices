package com.microservices.course.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    public String save(MultipartFile file ,String fileName ,String FilePath) throws IOException;

}
