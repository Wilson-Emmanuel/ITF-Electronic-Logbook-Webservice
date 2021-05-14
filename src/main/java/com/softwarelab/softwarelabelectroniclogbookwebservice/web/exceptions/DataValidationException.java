package com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
public class DataValidationException extends RuntimeException{
    public DataValidationException(String msg){
        super(msg);
    }
}
