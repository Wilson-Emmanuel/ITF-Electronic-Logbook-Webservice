package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.SchoolCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.SchoolUpdateRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Data
public class SchoolCreationJSON {

    @ApiModelProperty(notes = "School name. Expected format: String", required = true)
    @JsonProperty(value = "school_name")
    @NotEmpty
    @NotNull
    private String schoolName;

    @ApiModelProperty(notes = "school's State name. Expected format: String: valid state name in the system", required = true)
    @JsonProperty(value = "stateName")
    @NotEmpty
    @NotNull
    private String stateName;

    @ApiModelProperty(notes = "School address. Expected format: String", required = true)
    @NotEmpty
    @NotNull
    private String address;

    public SchoolCreationRequest getRequestModel(){
        return SchoolCreationRequest.builder()
                .schoolName(schoolName)
                .stateName(stateName)
                .address(address)
                .build();
    }
    public SchoolUpdateRequest getUpdateRequest(){
        return SchoolUpdateRequest.builder()
                .schoolName(schoolName)
                .stateName(stateName)
                .address(address)
                .build();
    }
}
