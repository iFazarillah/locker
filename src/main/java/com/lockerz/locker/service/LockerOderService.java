package com.lockerz.locker.service;

import com.lockerz.locker.dto.LockerOrderRequest;
import com.lockerz.locker.dto.LockerOrderResponse;
import com.lockerz.locker.dto.ReqRes;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LockerOderService {


    LockerOrderResponse orderLocker(LockerOrderRequest orderRequest) throws Exception;

}
