package com.lockerz.locker.service.impl;

import com.lockerz.locker.exception.NotFoundException;
import com.lockerz.locker.model.Users;
import com.lockerz.locker.repository.UsersRepository;
import com.lockerz.locker.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public Users getUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
