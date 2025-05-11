package com.microservices.course.exceptionHandler;


import com.microservices.course.dtos.CustomMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GolbalExceptionHandler {
@ExceptionHandler({ResourceNotFoundException.class})
    ResponseEntity<CustomMessage> handleResourceNotFoundException(ResourceNotFoundException ex){
    CustomMessage customMessage = new CustomMessage();
    customMessage.setMessage(ex.getMessage());
    customMessage.setSuccess(false);
    return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
}
}
