package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
public class APIResponseJSON<T> {
    private String message;

    private T data;

    public APIResponseJSON(String message) {
        this.message = message;
        this.data = null;
    }
    public APIResponseJSON(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
