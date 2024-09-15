package com.springboot.blog.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
//import com.springboot.blog.payload.RegisterDto;


public interface AuthService {
    String login(LoginDto loginDto);

    //String register(RegisterDto registerDto);
   String register(RegisterDto registerDto);
}