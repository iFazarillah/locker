package com.lockerz.locker.controller;

import com.lockerz.locker.dto.LockerOrderRequest;
import com.lockerz.locker.dto.ReqRes;
import com.lockerz.locker.service.LockerOderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rental")
public class LockerOrderController extends BaseController {


    @Autowired
    private LockerOderService lockerOderService;

    @PostMapping("/order")
    public ResponseEntity<Map<String, Object>> orderLocker(@RequestBody LockerOrderRequest orderRequest) throws Exception {
        return responseGeneralSuccess(lockerOderService.orderLocker(orderRequest));
    }
}
