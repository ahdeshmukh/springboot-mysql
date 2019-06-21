package com.deshmukhamit.springbootmysql.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Service
public class DateTimeService {

    public LocalDate convertStringToDate(String dateString) {
        try {
            return LocalDate.parse(dateString);
        } catch (DateTimeParseException | NullPointerException ex) {
            // here we should log the dateString value to understand what was the cause of this exception
            // if NullPointerException is thrown, there is no reason to log it
            return null;
        }
    }
}
