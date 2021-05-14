package com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
public class UnAuthorizedException extends RuntimeException{
    public UnAuthorizedException(String msg){
        super(msg);
    }
}
