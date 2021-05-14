package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.ManagerCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.NewUserRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
public class ManagerCreationJSON {

    @ApiModelProperty(notes = "Manager first name. Expected format: String", required = true)
    @JsonProperty(value = "first_name")
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String firstName;

    @ApiModelProperty(notes = "Manager last name. Expected format: String", required = true)
    @JsonProperty(value = "last_name")
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String lastName;

    @ApiModelProperty(notes = "Manager/company email. Expected format: String", required = true)
    @NotEmpty
    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @ApiModelProperty(notes = "Company/manager phone. Expected format: 11 digits", required = true)
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[0-9]{11}$",message = "Invalid phone number.")
    private String phone;

    @NotEmpty
    @NotNull
    @Size(max = 30, min = 6)
    private String password;

    @ApiModelProperty(notes = "Company name. Expected format: String", required = true)
    @JsonProperty(value = "company_name")
    @NotEmpty
    @NotNull
    private String companyName;

    @ApiModelProperty(notes = "Company address. Expected format: String", required = true)
    @JsonProperty(value = "company_address")
    @NotEmpty
    @NotNull
    private String companyAddress;

    @ApiModelProperty(notes = "type of company. Expected format: String",example = "Software", required = true)
    @JsonProperty(value = "company_type")
    @NotEmpty
    @NotNull
    private String companyType;

    @ApiModelProperty(notes = "company state. Expected format: String: valid state name in the system", required = true)
    @JsonProperty(value = "state_name")
    @NotEmpty
    @NotNull
    private String stateName;

    public ManagerCreationRequest getRequestModel(){
        return ManagerCreationRequest.builder()
                .newUserRequest(getNewUser())
                .companyAddress(companyAddress)
                .companyName(companyName)
                .companyType(companyType)
                .stateName(stateName)
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
