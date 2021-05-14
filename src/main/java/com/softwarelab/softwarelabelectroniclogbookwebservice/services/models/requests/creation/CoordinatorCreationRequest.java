package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class CoordinatorCreationRequest {
    private NewUserRequest newUser;
    private String department;
    private String schoolName;
}
