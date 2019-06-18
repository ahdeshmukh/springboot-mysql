package com.deshmukhamit.springbootmysql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean verifyPassword(String plainPassword, String encryptedPassword) {
        return passwordEncoder.matches(plainPassword, encryptedPassword);
    }

    public String encodePassword(String plainPassword) {
        /*if(plainPassword.isBlank()) {
            return null;
        }*/
        return passwordEncoder.encode(plainPassword);
    }
}
