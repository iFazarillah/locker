package com.lockerz.locker.service;

public interface EmailService {


    void sendPasswordLocker(String email, String orderId, String password);
}
