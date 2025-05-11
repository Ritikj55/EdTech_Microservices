package com.microservices.course.exceptionHandler;

public class ResourceNotFoundException extends  Exception {

    public ResourceNotFoundException(String message){
        super(message);
    }
    public ResourceNotFoundException(){
        super("Resource not found");
    }
}
