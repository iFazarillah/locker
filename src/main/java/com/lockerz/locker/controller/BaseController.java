package com.lockerz.locker.controller;

import com.lockerz.locker.helper.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class BaseController {

    public static ResponseEntity<Map<String, Object>> responseGeneralSuccess(Object object) {
        return ResponseWrapper.generalSuccess("Sukses", HttpStatus.OK.value(), object);
    }

    public static ResponseEntity<Map<String, Object>> responseDataCreated() {
        return ResponseWrapper.dataCreated("Data Created", HttpStatus.CREATED.value());
    }
}
