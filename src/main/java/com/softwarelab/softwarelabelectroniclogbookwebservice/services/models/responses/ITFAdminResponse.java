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
public class ITFAdminResponse {
    @JsonProperty(value = "itf_admin_id")
    private int id;

    @JsonProperty(value = "staff_number")
    private String staffNo;

    @JsonProperty(value = "branch")
    private String branch;

    @JsonProperty(value = "personal_details")
    private AppUserDetails personalDetail;
}
