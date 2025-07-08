package com.khan.hospital_management.security;

import com.khan.hospital_management.model.User;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String SECRET_KEY = "have-fun!";

    public String generateToken(User user) {
        return null;
    }
}
