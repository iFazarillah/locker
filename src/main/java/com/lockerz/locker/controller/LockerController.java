package com.lockerz.locker.controller;

import com.lockerz.locker.dto.ReqRes;
import com.lockerz.locker.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/locker")
public class LockerController extends BaseController {

    @Autowired
    private LockerService lockerService;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllLocker() throws Exception {
        return responseGeneralSuccess(lockerService.getAllLocker());
    }

}
