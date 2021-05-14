package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Data
@Builder
public class ManagerCreationRequest {
    private NewUserRequest newUserRequest;
    private String companyName;
    private String companyAddress;
    private String companyType;
    private String stateName;
}
