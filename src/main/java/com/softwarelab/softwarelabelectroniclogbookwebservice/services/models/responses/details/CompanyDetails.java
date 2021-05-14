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
public class CompanyDetails {
    @JsonProperty(value = "manager_name")
    private String managerName;

    @JsonProperty(value = "manager_email")
    private String managerEmail;

    @JsonProperty(value = "manager_phone")
    private String managerPhone;

    @JsonProperty(value = "company_name")
    private String companyName;

    @JsonProperty(value = "company_type")
    private String companyType;

    @JsonProperty(value = "company_address")
    private String companyAddress;

    @JsonProperty(value = "company_state")
    private String companyState;

}
