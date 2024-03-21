package com.lockerz.locker.service;

import com.lockerz.locker.model.Lockers;

import java.util.List;

public interface LockerService {

    List<Lockers> getAllLocker();

    Lockers findByLockerCode(String lockerCode) throws Exception;
}
