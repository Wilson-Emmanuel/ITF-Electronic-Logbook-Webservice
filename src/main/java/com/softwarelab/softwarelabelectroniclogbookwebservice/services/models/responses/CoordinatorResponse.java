package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details.AppUserDetails;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class CoordinatorResponse {
    @JsonProperty(value = "coordinator_id")
    private Long id;

    @JsonProperty(value = "personal_details")
    private AppUserDetails appUserDetails;

    @JsonProperty(value = "school")
    private SchoolResponse school;

    @JsonProperty(value = "department")
    private String department;
}
