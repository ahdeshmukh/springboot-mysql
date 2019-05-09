package com.deshmukhamit.springbootmysql.exception;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
        super("Invalid email or password");
    }
}
