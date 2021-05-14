package com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
public class ProcessViolationException extends RuntimeException{
    public ProcessViolationException(String msg){
        super(msg);
    }
}
