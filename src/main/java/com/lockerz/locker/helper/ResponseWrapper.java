package com.lockerz.locker.helper;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseWrapper {

    public static ResponseEntity<Map<String, Object>> generalSuccess(String message, int httpStatus, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", httpStatus);
        response.put("data", data);
        response.put("status", message);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    public static ResponseEntity<Map<String, Object>> dataCreated(String message, int httpStatus) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", httpStatus);
        response.put("message", message);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
