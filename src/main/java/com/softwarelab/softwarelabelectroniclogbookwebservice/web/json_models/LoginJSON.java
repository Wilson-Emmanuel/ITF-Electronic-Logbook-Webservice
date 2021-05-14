package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Data
public class LoginJSON {

    @ApiParam(name = "email", value = "user's email is the login username", example = "user@gmail.com", required = true)
    @NotEmpty
    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @ApiParam(name = "password", value = "User's login password", example = "user2353", required = true)
    @NotEmpty
    @NotNull
    @Size(max = 30, min = 6)
    private String password;
}
