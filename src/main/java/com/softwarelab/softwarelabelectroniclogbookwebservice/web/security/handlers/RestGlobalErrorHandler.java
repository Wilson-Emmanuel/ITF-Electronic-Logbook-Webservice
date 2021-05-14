package com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.APIResponseJSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@RestControllerAdvice
public class RestGlobalErrorHandler {

    @ExceptionHandler(value = {RuntimeException.class, Exception.class})
    public ResponseEntity<APIResponseJSON<String>> handleException(Exception exception) {
        APIResponseJSON<String> apiResponse = new APIResponseJSON<>("Request unsuccessful: ."+exception.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    //TODO: individual handlers
}
