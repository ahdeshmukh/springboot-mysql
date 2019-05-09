package com.deshmukhamit.springbootmysql.exception;
import java.util.ArrayList;
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
    public final ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(this.getCustomErrorResponse(httpStatus, ex.getLocalizedMessage()), httpStatus);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public final ResponseEntity<CustomErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.PRECONDITION_FAILED;
        return new ResponseEntity<>(this.getCustomErrorResponse(httpStatus, ex.getLocalizedMessage()), httpStatus);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<CustomErrorResponse> handleAuthHeaderException(AccessDeniedException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(this.getCustomErrorResponse(httpStatus, ex.getLocalizedMessage()), httpStatus);
    }

    @ExceptionHandler(LoginFailedException.class)
    public final ResponseEntity<CustomErrorResponse> handleLoginFailedException(LoginFailedException ex) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(this.getCustomErrorResponse(httpStatus, ex.getLocalizedMessage()), httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(this.getCustomErrorResponse(httpStatus, "Validation Failed", details), httpStatus);
    }

    private CustomErrorResponse getCustomErrorResponse(HttpStatus httpStatus, String message) {
        return new CustomErrorResponse(httpStatus.getReasonPhrase(), httpStatus.value(), message);
    }
    private CustomErrorResponse getCustomErrorResponse(HttpStatus httpStatus, String message, List<String> details) {
        return new CustomErrorResponse(httpStatus.getReasonPhrase(), httpStatus.value(), message, details);
    }

}
