package com.deshmukhamit.springbootmysql.exception;

public class ErrorDetails {
    private String message;
    private String details;

    ErrorDetails(String message, String details) {
        super();
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

}
