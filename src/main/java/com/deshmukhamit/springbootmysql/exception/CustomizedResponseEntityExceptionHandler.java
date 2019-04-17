package com.deshmukhamit.springbootmysql.exception;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // this does not show the error message and details
    /*@ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        return new ResponseEntity<>(this.getErrorDetails(ex.getMessage(), details), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(this.getErrorDetails(ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ErrorDetails> handleAuthHeaderException(AccessDeniedException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        //for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(ex.getLocalizedMessage());
        //}
        return new ResponseEntity<>(this.getErrorDetails("Access Denied", details), HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return new ResponseEntity(this.getErrorDetails("Validation Failed", details), HttpStatus.BAD_REQUEST);
    }

    private ErrorDetails getErrorDetails(String message, List<String> details) {
        return new ErrorDetails(message, details);
    }

    private ErrorDetails getErrorDetails(String message) {
        return new ErrorDetails(message);
    }

}
