package com.video.service.exceptions;

import com.video.service.dto.CustomMessage;
import com.video.service.dto.CustomPageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    ResponseEntity<CustomMessage> handleResourceNotFound(ResourceNotFoundException resourceNotFoundException){
         CustomMessage customMessage =  new CustomMessage();
         customMessage.setMessage(resourceNotFoundException.getMessage());
         customMessage.setSuccess(false);
        return  new ResponseEntity<>(customMessage, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    ResponseEntity<String> handleMethodNotFoundException(MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();
        Map<String,String> map = new HashMap<>();
        result.getAllErrors().forEach(error -> {
            String field = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            map.put(field,message);
        });
        return new ResponseEntity<>(map.toString(),HttpStatus.BAD_REQUEST);
    }



}
