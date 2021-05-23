package com.softwarelab.softwarelabelectroniclogbookwebservice.web.controllers;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.APIResponseJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.ITFAdminResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.ITFAdminService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.StudentService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.ITFAdminCreationJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.ITFAdminUpdateJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.UpdatePassword;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
//@Secured("ITF")
@Validated
@Api(tags = "Handles operation of ITF Admins")
@RestController
@RequestMapping(value = "/v1/itf/", headers = {"Authorization", "x-client-request-key"})
public class ITFAdminController {

    ITFAdminService adminService;
    StudentService studentService;

    public ITFAdminController(ITFAdminService adminService, StudentService studentService) {
        this.adminService = adminService;
        this.studentService = studentService;
    }

   @ApiOperation(value = "Creates new ITF Admin", notes = "")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<ITFAdminResponse>> createITFAdmin(@RequestBody @Valid ITFAdminCreationJSON creationJSON){

        ITFAdminResponse response = adminService.saveITFAdmin(creationJSON.getRequestModel());
        APIResponseJSON<ITFAdminResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Update an existing ITF Admin", notes = "")
    @PutMapping(value = "{adminId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<ITFAdminResponse>> updateITFAdminInfo(@PathVariable("adminId") @Valid @Min(value = 1) Integer adminId,
                                                                            @RequestBody @Valid ITFAdminUpdateJSON creationJSON){

        ITFAdminResponse response = adminService.updateITFAdmin(creationJSON.getRequestModel(),adminId);
        APIResponseJSON<ITFAdminResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @Secured("ITF")
    @ApiOperation(value = "Get an existing ITF Admin", notes = "")
    @GetMapping(value = "{adminId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<ITFAdminResponse>> fetchITFAdmin(@PathVariable("adminId") @Valid @Min(value = 1) Integer adminId){

        ITFAdminResponse response = adminService.getUserById(adminId);
        APIResponseJSON<ITFAdminResponse> apiResponseJSON = new APIResponseJSON<>("Successfully retrieved",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

   @ApiOperation(value = "Update Admin Password", notes = "")
    @PutMapping(value = "change-password/{adminId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<ITFAdminResponse>> updateITFAdminPassword(@PathVariable("adminId") @Valid @Min(value = 1) Integer adminId,
                                                                            @RequestBody @Valid UpdatePassword updatePassword){

        ITFAdminResponse response = adminService.updateUserPassword(updatePassword.getPassword(), adminId);
        APIResponseJSON<ITFAdminResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Mark student as paid", notes = "")
    @Secured("ITF")
    @PutMapping(value = "pay-student/{studentId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<String>> payStudent(@PathVariable("studentId") @Valid @Min(value = 1) Long studentId){

        studentService.payStudent(studentId);
        APIResponseJSON<String> apiResponseJSON = new APIResponseJSON<>("Successfully processed");
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Sign student logbook", notes = "")
    @Secured("ITF")
    @PutMapping(value = "sign-logbook/{studentId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<String>> signStudentLogbook(@PathVariable("studentId") @Valid @Min(value = 1) Long studentId){

        studentService.signLogBook(studentId);
        APIResponseJSON<String> apiResponseJSON = new APIResponseJSON<>("Successfully processed");
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }


}
