package com.deshmukhamit.springbootmysql.exception;

import java.util.List;

public class CustomErrorResponse {
    private String error;
    private String message;
    private int status;
    private List<String> details;

    CustomErrorResponse(String error, int status, String message) {
        super();
        this.error = error;
        this.message = message;
        this.status = status;
    }

    CustomErrorResponse(String error, int status, String message, List<String> details) {
        super();
        this.error = error;
        this.message = message;
        this.status = status;
        this.details = details;
    }

    public String getError() {
        return this.error;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return this.status;
    }

    public List<String> getDetails() {
        return this.details;
    }
}
