package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Data
@Builder
public class LoginToken {
    @JsonProperty(value = "token")
    private String token;
}
