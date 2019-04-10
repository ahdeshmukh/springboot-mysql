package com.deshmukhamit.springbootmysql.exception;

import java.util.Date;

public class ErrorDetails {
    //private Date timestamp;
    private String message;
    private String details;
    private String status;

    public ErrorDetails(String message, String details, String status) {
        super();
        //this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.status = status;
    }

    /*public Date getTimestamp() {
        return timestamp;
    }*/

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getErrorOccured() {
        return true;
    }

}
