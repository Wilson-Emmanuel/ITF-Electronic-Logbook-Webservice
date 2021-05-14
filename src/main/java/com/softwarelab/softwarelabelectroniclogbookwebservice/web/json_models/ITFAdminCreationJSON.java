package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.ITFAdminCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.NewUserRequest;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
public class ITFAdminCreationJSON {

    @JsonProperty(value = "first_name")
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String firstName;

    @JsonProperty(value = "last_name")
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String lastName;

    @NotEmpty
    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[0-9]{11}$",message = "Invalid phone number.")
    private String phone;

    @NotEmpty
    @NotNull
    @Size(max = 30, min = 6)
    private String password;

    @JsonProperty(value = "staff_number")
    @NotEmpty
    @NotNull
    private String staffNo;

    @NotEmpty
    @NotNull
    private String branch;

    public ITFAdminCreationRequest getRequestModel(){
        return ITFAdminCreationRequest.builder()
                .newUser(getNewUser())
                .branch(branch)
                .staffNo(staffNo)
                .build();
    }

    private NewUserRequest getNewUser(){
        return NewUserRequest.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .phone(phone)
                .build();
    }
}
