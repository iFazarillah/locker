package com.lockerz.locker.service;

import com.lockerz.locker.exception.NotFoundException;
import com.lockerz.locker.model.Users;

public interface UsersService {


    Users getUserByEmail(String email) throws Exception;
}
