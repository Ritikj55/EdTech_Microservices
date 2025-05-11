package com.category.service.exceptions;

import com.category.service.dto.CustomMessage;
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
    ResponseEntity<CustomMessage> handleResourceNotFound(ResourceNotFoundException ex) {
        CustomMessage customMessage = new CustomMessage();
        customMessage.setMessage(ex.getMessage());
        customMessage.setSuccess(false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String,String>>  handleMethodArgument(MethodArgumentNotValidException exception) {
        Map<String, String> map = new HashMap<>();
        BindingResult bindingResult = exception.getBindingResult();
        bindingResult.getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String  message = error.getDefaultMessage();
            map.put(field,message);
        });
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }
}
