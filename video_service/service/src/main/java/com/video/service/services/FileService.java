package com.video.service.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String save (MultipartFile inputFile , String outputFile , String fileName) throws IOException;

}
