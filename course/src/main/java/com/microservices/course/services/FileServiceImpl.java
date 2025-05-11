package com.microservices.course.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileServiceImpl implements  FileService{
    @Override
    public String save(MultipartFile file, String fileName, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path);
        Path rootFilePath = Paths.get(path.toString(),fileName);
        Files.copy(file.getInputStream(),rootFilePath, StandardCopyOption.REPLACE_EXISTING);
        return rootFilePath.toString();
    }
}
