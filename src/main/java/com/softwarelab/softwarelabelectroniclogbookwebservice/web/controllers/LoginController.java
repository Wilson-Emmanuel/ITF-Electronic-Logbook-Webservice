package com.softwarelab.softwarelabelectroniclogbookwebservice.web.controllers;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto.LoginToken;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.APIResponseJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.LoginService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.LoginJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers.AuthenticatedUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Validated
@Api(tags = "Login endpoints",  description = "Handles user authentication.")
@RestController
@RequestMapping(value = "/v1/auth/")
public class LoginController {

    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @ApiOperation(value = "Authenticates users", notes = "Emails as username")
    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<LoginToken>> login(@RequestBody @Valid LoginJSON request){

        LoginToken loginToken = loginService.login(request.getEmail(), request.getPassword());
        APIResponseJSON<LoginToken> apiResponseJSON = new APIResponseJSON<>("Successfully logged in.", loginToken);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }


   @ApiOperation(value = "Logs user out", notes = "")
    @PostMapping(value = "logout",produces = MediaType.APPLICATION_JSON_VALUE, headers = {"Authorization","x-client-request-key"})
    public ResponseEntity<APIResponseJSON<String>> logout(@ApiIgnore @AuthenticationPrincipal AuthenticatedUser authenticatedUser){

        loginService.logout(authenticatedUser);
        APIResponseJSON<String> apiResponseJSON = new APIResponseJSON<>("Successfully logged out.");
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }
}
