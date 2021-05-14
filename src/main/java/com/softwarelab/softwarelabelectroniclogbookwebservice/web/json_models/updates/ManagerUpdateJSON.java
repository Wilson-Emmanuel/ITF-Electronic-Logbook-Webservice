package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.ManagerUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.UserUpdateRequest;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Data
public class ManagerUpdateJSON {
    @NotEmpty
    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[0-9]{11}$",message = "Invalid phone number.")
    private String phone;

    @JsonProperty(value = "company_name")
    @NotEmpty
    @NotNull
    private String companyName;

    @JsonProperty(value = "company_address")
    @NotEmpty
    @NotNull
    private String companyAddress;

    @JsonProperty(value = "company_type")
    @NotEmpty
    @NotNull
    private String companyType;

    @JsonProperty(value = "state_name")
    @NotEmpty
    @NotNull
    private String stateName;

    private UserUpdateRequest getUser(){
        return UserUpdateRequest.builder()
                .email(email)
                .phone(phone)
                .build();
    }

    public ManagerUpdateRequest getRequestModel(){
        return ManagerUpdateRequest.builder()
                .userUpdateRequest(getUser())
                .companyAddress(companyAddress)
                .companyName(companyName)
                .companyType(companyType)
                .stateName(stateName)
                .build();
    }
}
