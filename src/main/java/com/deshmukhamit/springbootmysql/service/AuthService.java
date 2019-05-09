package com.deshmukhamit.springbootmysql.service;

import com.deshmukhamit.springbootmysql.exception.LoginFailedException;
import com.deshmukhamit.springbootmysql.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User login(String email, String password) {
        Boolean success = false;
        User user;

        if(email.isBlank() || password.isBlank()) {
            // why bother querying the DB if either email or password is blank
            throw new LoginFailedException();
        }

        try {
            user = userService.getUserByEmail(email).get();
        } catch (Exception ex) { // need to catch actual exception
            throw new LoginFailedException();
        }

        if(user.getId().longValue() > 0) {
           success = passwordEncoder.matches(password, user.getPassword());
        }

        if(!success) {
            throw new LoginFailedException();
        }

        return user;
    }
}
