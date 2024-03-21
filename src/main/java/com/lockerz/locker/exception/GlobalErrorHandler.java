package com.lockerz.locker.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public Map<String, Object> handleNotFoundException(NotFoundException ex){
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("status", 404);
        errorMap.put("message", ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Map<String, Object> handleInvalidArgumentSingle(ValidationException ex){

        Map<String, Object> errorEx = new HashMap<>();
        errorEx.put("status",400);
        errorEx.put("message", ex.getMessage());

        return errorEx;

    }



    
}
