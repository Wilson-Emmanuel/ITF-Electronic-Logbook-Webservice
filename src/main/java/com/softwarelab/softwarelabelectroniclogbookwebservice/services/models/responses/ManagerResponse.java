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
public class ManagerResponse {
    @JsonProperty(value = "manager_id")
    private Long id;

    @JsonProperty(value = "personal_details")
    private AppUserDetails personalDetail;

    @JsonProperty(value = "company_name")
    private String companyName;

    @JsonProperty(value = "company_address")
    private String companyAddress;

    @JsonProperty(value = "company_type")
    private String companyType;

    @JsonProperty(value = "company_state")
    private String companyState;
}
