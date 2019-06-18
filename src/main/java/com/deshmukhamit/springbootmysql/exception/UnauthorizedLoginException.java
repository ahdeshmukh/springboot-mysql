package com.deshmukhamit.springbootmysql.exception;

// exception for unauthorized login attempt.
// eg: If a user who has been deactivated tries to log in

public class UnauthorizedLoginException extends RuntimeException {
    public UnauthorizedLoginException() {
        super("Unauthorized login attempt. This will be reported");
    }
}
