package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Thu, 13/05/2021.
 */
@Data
@Builder
public class UserAuthDTO {
    private String email;
    private String hashedPassword;
    private String userType;
}
