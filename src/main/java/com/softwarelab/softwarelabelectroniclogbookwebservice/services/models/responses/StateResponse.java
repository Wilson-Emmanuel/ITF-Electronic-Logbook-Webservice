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
public class StateResponse {
    @JsonProperty(value = "state_name")
    private String name;

    @JsonProperty(value = "state_id")
    private int id;
}
