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

    @PostMapping("/deposit")
    public ResponseEntity<Map<String, Object>> depositMoney(@RequestBody LockerOrderRequest orderRequest) throws Exception {
        return responseGeneralSuccess(lockerOderService.depositMoney(orderRequest));
    }

    @PostMapping("/lock")
    public ResponseEntity<Map<String, Object>> lockLocker(@RequestBody ReqRes request) throws Exception {
        return responseGeneralSuccess(lockerOderService.lockLocker(request));
    }

    @PostMapping("/lock/bulk")
    public ResponseEntity<Map<String, Object>> bulkLockLocker(@RequestBody List<ReqRes> request) throws Exception {
        return responseGeneralSuccess(lockerOderService.bulkLockLocker(request));
    }

    @PostMapping("/unlock")
    public ResponseEntity<Map<String, Object>> unlockLocker(@RequestBody ReqRes request) throws Exception {
        return responseGeneralSuccess(lockerOderService.unlockLocker(request));
    }

    @PostMapping("/unlock/deposit")
    public ResponseEntity<Map<String, Object>> unlockLockerByDeposit(@RequestBody ReqRes request) throws Exception {
        return responseGeneralSuccess(lockerOderService.unlockLockerByDeposit(request));
    }

}
