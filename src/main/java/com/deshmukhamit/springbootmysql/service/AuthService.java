package com.deshmukhamit.springbootmysql.service;

import com.deshmukhamit.springbootmysql.exception.LoginFailedException;
import com.deshmukhamit.springbootmysql.exception.ResourceNotFoundException;
import com.deshmukhamit.springbootmysql.exception.UnauthorizedLoginException;
import com.deshmukhamit.springbootmysql.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    public User login(String email, String password)
            throws LoginFailedException, UnauthorizedLoginException {

        boolean success = false;
        User user;

        if(email.isBlank() || password.isBlank()) {
            // why bother querying the DB if either email or password is blank
            throw new LoginFailedException();
        }

        try {
            user = userService.getUserByEmail(email);
        } catch (ResourceNotFoundException ex) { // user with given email not found
            throw new LoginFailedException();
        } catch (Exception ex) { // safety net
            throw new LoginFailedException();
        }

        if(user.getId().longValue() > 0) {
           success = passwordService.verifyPassword(password, user.getPassword());
        }

        if(!success) {
            throw new LoginFailedException();
        }

        if(user.getActive() == 0) {
            throw new UnauthorizedLoginException();
        }

        return user;
    }

}
