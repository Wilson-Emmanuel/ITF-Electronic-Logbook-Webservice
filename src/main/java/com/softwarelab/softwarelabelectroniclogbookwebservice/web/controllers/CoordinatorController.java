package com.softwarelab.softwarelabelectroniclogbookwebservice.web.controllers;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.CoordinatorQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.APIResponseJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.CoordinatorResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.CoordinatorService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.StudentService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.CoordinatorCreationJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.CoordinatorRemarkUpdateJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.UpdatePassword;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.UserUpdateJSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Validated
@Api(tags = "Handles operation of IT Coordinators")
@RestController
@RequestMapping(value = "/v1/coordinator/", headers = {"Authorization", "x-client-request-key"})
public class CoordinatorController {

    CoordinatorService coordinatorService;
    StudentService studentService;

    public CoordinatorController(CoordinatorService coordinatorService, StudentService studentService) {
        this.coordinatorService = coordinatorService;
        this.studentService = studentService;
    }

    //@Secured({"COORDINATOR","ITF"})
    @ApiOperation(value = "Creates new Coordinator", notes = "")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<CoordinatorResponse>> createCoordinator(@RequestBody @Valid CoordinatorCreationJSON creationJSON){

        CoordinatorResponse response = coordinatorService.saveCoordinator(creationJSON.getRequestModel());
        APIResponseJSON<CoordinatorResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    //@Secured({"COORDINATOR","ITF"})
    @ApiOperation(value = "Update an existing Coordinator", notes = "")
    @PutMapping(value = "{coordinatorId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<CoordinatorResponse>> updateCoordinatorInfo(@PathVariable("coordinatorId") @Valid @Min(value = 1) Long coordinatorId,
                                                                            @RequestBody @Valid UserUpdateJSON creationJSON){

        CoordinatorResponse response = coordinatorService.updateCoordinator(creationJSON.getUser(),coordinatorId);
        APIResponseJSON<CoordinatorResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    //@Secured({"COORDINATOR","ITF"})
    @ApiOperation(value = "Get an existing Coordinator", notes = "")
    @GetMapping(value = "{coordinatorId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<CoordinatorResponse>> fetchCoordinator(@PathVariable("coordinatorId") @Valid @Min(value = 1) Long coordinatorId){

        CoordinatorResponse response = coordinatorService.getUserById(coordinatorId);
        APIResponseJSON<CoordinatorResponse> apiResponseJSON = new APIResponseJSON<>("Successfully retrieved",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    //@Secured({"COORDINATOR","ITF"})
    @ApiOperation(value = "Update Coordinator Password", notes = "")
    @PutMapping(value = "change-password/{coordinatorId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<CoordinatorResponse>> updateCoordinatorPassword(@PathVariable("coordinatorId") @Valid @Min(value = 1) Long coordinatorId,
                                                                            @RequestBody @Valid UpdatePassword updatePassword){

        CoordinatorResponse response = coordinatorService.updateUserPassword(updatePassword.getPassword(), coordinatorId);
        APIResponseJSON<CoordinatorResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    //@Secured("COORDINATOR")
    @ApiOperation(value = "Update Student remark", notes = "")
    @PutMapping(value = "update-remark/{studentId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<String>> updateStudentRemark(@PathVariable("studentId") @Valid @Min(value = 1) Long studentId,
                                                                       @RequestBody @Valid CoordinatorRemarkUpdateJSON updateJSON){

        studentService.updateCoordinatorRemark(updateJSON.getCoordinatorRemark(),studentId);
        APIResponseJSON<String> apiResponseJSON = new APIResponseJSON<>("Successfully processed");
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    //@Secured("ITF")
    @ApiOperation(value = "Search Coordinators ")
    @GetMapping(value = "search",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<PagedData<CoordinatorResponse>>> searchCoordinators(@RequestParam(value = "email", required = false) String email,
                                                                                        @RequestParam(value = "school_name", required = false) String schoolName,
                                                                                        @RequestParam(value = "state_name", required = false) String stateName,
                                                                                        @RequestParam(value = "phone", required = false) String phone,
                                                                                        @RequestParam(value = "size", required = false, defaultValue = "50") int size,
                                                                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page){
        CoordinatorQueryRequest coordinatorQueryRequest = CoordinatorQueryRequest.builder()
                .email(email)
                .schoolName(schoolName)
                .state(stateName)
                .phone(phone)
                .build();
        PagedData<CoordinatorResponse> response =coordinatorService.getCoordinators(coordinatorQueryRequest,page,size);
        APIResponseJSON<PagedData<CoordinatorResponse>> apiResponseJSON = new APIResponseJSON<>("Successfully retrieved",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }


}
