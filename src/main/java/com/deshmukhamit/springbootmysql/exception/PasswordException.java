package com.deshmukhamit.springbootmysql.exception;

public class PasswordException extends RuntimeException{
    public PasswordException( String message) {
        super(message);
    }
}
