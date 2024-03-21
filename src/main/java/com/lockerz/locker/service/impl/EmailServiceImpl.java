package com.lockerz.locker.service.impl;

import com.lockerz.locker.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Override
    public void sendPasswordLocker(String email, String orderId, String password) {

        String body = String.format("""
                Hello! thank you for using our services 
                
                Order ID        : %s
                Password        : %s
                
                Keep the password safe and have a good day!
                """, orderId, password);
        sendEmail(email, "LOCKER PASSWORD INFO | LOCKERZ",body);
    }
}
