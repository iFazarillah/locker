package com.lockerz.locker.service;

import com.lockerz.locker.dto.LockerOrderRequest;
import com.lockerz.locker.dto.LockerOrderResponse;
import com.lockerz.locker.dto.ReqRes;
import com.lockerz.locker.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LockerOderService {


    LockerOrderResponse orderLocker(LockerOrderRequest orderRequest) throws Exception;

    LockerOrderResponse depositMoney(LockerOrderRequest orderRequest) throws ValidationException;

    ReqRes lockLocker(ReqRes request) throws ValidationException;

    List<ReqRes> bulkLockLocker(List<ReqRes> request) throws Exception;

    ReqRes unlockLocker(ReqRes request) throws Exception;

    ReqRes unlockLockerByDeposit(ReqRes request) throws Exception;
}
