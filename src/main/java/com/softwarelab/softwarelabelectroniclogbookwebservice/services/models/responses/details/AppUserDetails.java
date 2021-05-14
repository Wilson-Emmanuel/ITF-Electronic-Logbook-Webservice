package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class AppUserDetails {
    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "phone")
    private String phone;

    @JsonProperty(value = "user_type")
    private String userType;
}
