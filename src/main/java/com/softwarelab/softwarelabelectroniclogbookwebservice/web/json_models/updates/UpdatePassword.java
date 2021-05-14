package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Data
public class UpdatePassword {
    @NotEmpty
    @NotNull
    @Size(max = 30, min = 6)
    private String password;
}
