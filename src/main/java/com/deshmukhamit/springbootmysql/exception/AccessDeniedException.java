package com.deshmukhamit.springbootmysql.exception;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException( String message) {
        super(message);
    }
}
