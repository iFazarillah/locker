package com.lockerz.locker.service.impl;

import com.lockerz.locker.exception.NotFoundException;
import com.lockerz.locker.model.Lockers;
import com.lockerz.locker.repository.LockersRepository;
import com.lockerz.locker.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockerServiceImpl implements LockerService {

    @Autowired
    private LockersRepository lockersRepository;

    @Override
    public List<Lockers> getAllLocker() {
        return lockersRepository.findAll();
    }

    @Override
    public Lockers findByLockerCode(String lockerCode) throws Exception {
        return lockersRepository.findById(lockerCode).orElseThrow(() -> new NotFoundException("Locker not found"));
    }
}
