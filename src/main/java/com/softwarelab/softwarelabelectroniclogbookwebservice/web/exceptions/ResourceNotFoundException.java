package com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    public ResourceNotFoundException(String resourceName){
        super(String.format("%s does not exist.",resourceName));
        this.resourceName = resourceName;
    }
    public ResourceNotFoundException(String message, String resourseName){
        super(message);
        this.resourceName = resourseName;
    }
    public String getResourceName(){
        return this.resourceName;
    }
}
