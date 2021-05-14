package com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.LoggedInUser;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.LoginToken;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.enums.UserType;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details.AppUserDetails;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers.AuthenticatedUser;

import java.util.Optional;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
public interface LoginService {
     String saveAuthUser(String email, String userType);
    LoginToken login(String username, String password);
    boolean logout(AuthenticatedUser authenticatedUser);
     Optional<LoggedInUser> getUserDetailsByToken(String token);
    void deleteLoginDetails(String email);
}
