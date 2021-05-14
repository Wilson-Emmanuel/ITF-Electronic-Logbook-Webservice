package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.UserUpdateRequest;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Data
public class UserUpdateJSON {
    @NotEmpty
    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[0-9]{11}$",message = "Invalid phone number.")
    private String phone;

    public UserUpdateRequest getUser(){
        return UserUpdateRequest.builder()
                .email(email)
                .phone(phone)
                .build();
    }
}
