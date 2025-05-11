package com.video.service.exceptions;

import java.util.function.Supplier;

public class ResourceNotFoundException extends  Exception {


    public ResourceNotFoundException() {
        super("Resource not found ");
    }
    public ResourceNotFoundException(String message){
        super(message);
    }


}
