package com.lockerz.locker.service.auth;

import com.lockerz.locker.dto.ReqRes;
import com.lockerz.locker.exception.NotFoundException;
import com.lockerz.locker.exception.ValidationException;
import com.lockerz.locker.model.Users;
import com.lockerz.locker.model.UsersDetails;
import com.lockerz.locker.repository.UserDetailsRepository;
import com.lockerz.locker.repository.UsersRepository;
import com.lockerz.locker.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Users signUp(ReqRes registrationRequest) throws Exception {

        try {
            UsersDetails userDetails = new UsersDetails();
            userDetails.setIdCardNumber(registrationRequest.getIdCardNumber());
            userDetails.setPhoneNumber(registrationRequest.getPhoneNumber());
            userDetailsRepository.saveAndFlush(userDetails);

            Users user = new Users();
            user.setUserDetails(userDetails);
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(registrationRequest.getRole());

            userRepository.saveAndFlush(user);

            return user;
        } catch (Exception e) {
            throw new ValidationException("Failed Register User");
        }
    }

    public ReqRes signIn(ReqRes signInRequest) throws Exception {
        ReqRes response = new ReqRes();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
            Users user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new NotFoundException("User not found"));
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24 Hours");

            return response;
        } catch (Exception e){
            throw new ValidationException("Failed Sign In User");
        }
    }

    public ReqRes refreshToken(ReqRes refreshTokenRequest) throws NotFoundException, ValidationException {

        ReqRes response = new ReqRes();
        String userEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
        Users user = userRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundException("User not found"));
        if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("24 Hours");
            return response;
        }

        throw new ValidationException("Failed refresh token");

    }
}
