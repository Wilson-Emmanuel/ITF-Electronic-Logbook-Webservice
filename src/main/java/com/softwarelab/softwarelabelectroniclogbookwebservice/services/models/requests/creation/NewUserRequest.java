package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class NewUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
}
