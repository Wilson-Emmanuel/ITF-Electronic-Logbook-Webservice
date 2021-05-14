package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Data
@Builder
public class ITFAdminCreationRequest {
    private String staffNo;
    private String branch;
    private NewUserRequest newUser;
}
