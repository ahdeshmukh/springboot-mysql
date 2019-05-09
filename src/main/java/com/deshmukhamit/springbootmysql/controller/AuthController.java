package com.deshmukhamit.springbootmysql.controller;

import com.deshmukhamit.springbootmysql.model.User;
import com.deshmukhamit.springbootmysql.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> json) {
        String email = json.getOrDefault("email", "");
        String password = json.getOrDefault("password", "");

        return authService.login(email, password);
    }
}
