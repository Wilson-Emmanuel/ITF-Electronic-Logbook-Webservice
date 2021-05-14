package com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
public class ResourceAlreadyExistsException extends RuntimeException{
    private String resourceName;
    public ResourceAlreadyExistsException(String resourceName){
        super(String.format("%s already exists.",resourceName));
        this.resourceName =resourceName;
    }
    public ResourceAlreadyExistsException(String message, String resourseName){
        super(message);
        this.resourceName = resourseName;
    }
    public String getResourceName(){
        return this.resourceName;
    }
}
