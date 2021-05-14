package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class SchoolDetails {
    @JsonProperty(value = "school_name")
    private String schoolName;

    @JsonProperty(value = "school_state")
    private String schoolState;

    @JsonProperty(value = "school_address")
    private String schoolAddress;

    @JsonProperty(value = "department")
    private String department;

    @JsonProperty(value = "coordinator_name")
    private String coordinatorName;

    @JsonProperty(value = "coordinator_phone")
    private String coordinatorPhone;

    @JsonProperty(value = "coordinator_email")
    private String coordinatorEmail;

    @JsonProperty(value = "coordinator_remark")
    private String coordinatorRemark;
}
