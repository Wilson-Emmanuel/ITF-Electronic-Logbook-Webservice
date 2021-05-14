package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class SchoolResponse {
    @JsonProperty(value = "school_id")
    private Integer id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "address")
    private String address;

    @JsonProperty(value = "state")
    private String state;
}
