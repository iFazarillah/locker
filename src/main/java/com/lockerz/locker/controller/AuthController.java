package com.lockerz.locker.controller;

import com.lockerz.locker.dto.ReqRes;
import com.lockerz.locker.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {


    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody ReqRes signUpRequest) throws Exception {
        return responseGeneralSuccess(authService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody ReqRes signUpRequest) throws Exception {
        return responseGeneralSuccess(authService.signIn(signUpRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestBody ReqRes signUpRequest) throws Exception {
        return responseGeneralSuccess(authService.refreshToken(signUpRequest));
    }



}
