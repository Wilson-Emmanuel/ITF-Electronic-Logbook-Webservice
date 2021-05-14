package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.ITFAdminUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.UserUpdateRequest;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Data
public class ITFAdminUpdateJSON {

    @NotEmpty
    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[0-9]{11}$",message = "Invalid phone number.")
    private String phone;

    @JsonProperty(value = "staff_number")
    @NotEmpty
    @NotNull
    private String staffNo;

    @NotEmpty
    @NotNull
    private String branch;

    private UserUpdateRequest getUser(){
        return UserUpdateRequest.builder()
                .email(email)
                .phone(phone)
                .build();
    }
    public ITFAdminUpdateRequest getRequestModel(){
        return ITFAdminUpdateRequest.builder()
                .updateRequest(getUser())
                .staffNo(staffNo)
                .branch(branch)
                .build();
    }

}
