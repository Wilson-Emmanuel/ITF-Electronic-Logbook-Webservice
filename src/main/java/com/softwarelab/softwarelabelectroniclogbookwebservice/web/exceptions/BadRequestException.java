package com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg){
        super(msg);
    }
}
