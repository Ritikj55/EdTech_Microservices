package com.video.service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements  FileService{
    @Override
    public String save(MultipartFile inputFile, String outputFile, String fileName) throws IOException {

        Path path = Paths.get(outputFile);
        Files.createDirectories(path);
        Path rootFilePath = Paths.get(path.toString(),inputFile.getOriginalFilename());
        Files.copy(inputFile.getInputStream(),rootFilePath, StandardCopyOption.REPLACE_EXISTING);
        return rootFilePath.toString();

    }
}
