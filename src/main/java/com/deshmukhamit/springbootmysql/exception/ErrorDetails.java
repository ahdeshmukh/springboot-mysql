package com.deshmukhamit.springbootmysql.exception;

import java.util.List;

public class ErrorDetails {
    private String message;
    private List<String> details;

    ErrorDetails(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    ErrorDetails(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }

}
