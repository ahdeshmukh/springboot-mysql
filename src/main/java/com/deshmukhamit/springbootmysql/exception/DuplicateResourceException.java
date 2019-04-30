package com.deshmukhamit.springbootmysql.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException( String resourceName, String fieldName, Object fieldValue) {
        // adding a message
        super(String.format("%s with %s : %s already exists", resourceName, fieldName, fieldValue));
    }

}
