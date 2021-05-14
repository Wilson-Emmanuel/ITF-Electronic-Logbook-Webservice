package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.enums.UserType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoggedInUser {
    String token;
    String email;
    UserType userType;
    LocalDateTime loggedIntime;
}
