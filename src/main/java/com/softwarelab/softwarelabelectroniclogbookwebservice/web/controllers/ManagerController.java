package com.softwarelab.softwarelabelectroniclogbookwebservice.web.controllers;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.ManagerQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.APIResponseJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.ManagerResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.ManagerService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.StudentService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.ManagerCreationJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.ManagerUpdateJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.UpdatePassword;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.WeeklySignUpdateJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.security.handlers.AuthenticatedUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Validated
@Api(tags = "Handles operation of IT Managers")
@RestController
@RequestMapping(value = "/v1/manager/", headers = {"Authorization", "x-client-request-key"})
public class ManagerController {

    ManagerService managerService;
    StudentService studentService;

    public ManagerController(ManagerService managerService, StudentService studentService) {
        this.managerService = managerService;
        this.studentService = studentService;
    }

    @ApiOperation(value = "Creates new Manager", notes = "")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<ManagerResponse>> createManager(@RequestBody @Valid ManagerCreationJSON creationJSON){

        ManagerResponse response = managerService.saveManager(creationJSON.getRequestModel());
        APIResponseJSON<ManagerResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Update an existing Manager", notes = "")
    @PutMapping(value = "{managerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<ManagerResponse>> updateManagerInfo(@PathVariable("managerId") @Valid @Min(value = 1) Long managerId,
                                                                            @RequestBody @Valid ManagerUpdateJSON creationJSON){

        ManagerResponse response = managerService.updateManager(creationJSON.getRequestModel(),managerId);
        APIResponseJSON<ManagerResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Get an existing Manager", notes = "")
    @GetMapping(value = "{managerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<ManagerResponse>> fetchManager(@PathVariable("managerId") @Valid @Min(value = 1) Long managerId){

        ManagerResponse response = managerService.getUserById(managerId);
        APIResponseJSON<ManagerResponse> apiResponseJSON = new APIResponseJSON<>("Successfully retrieved",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Update Manager Password", notes = "")
    @PutMapping(value = "change-password/{managerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<ManagerResponse>> updateManagerPassword(@PathVariable("managerId") @Valid @Min(value = 1) Long managerId,
                                                                            @RequestBody @Valid UpdatePassword updatePassword){

        ManagerResponse response = managerService.updateUserPassword(updatePassword.getPassword(), managerId);
        APIResponseJSON<ManagerResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Endorse/Sign student weekly task", notes = "")
    @PutMapping(value = "sign-week/{managerId}/{studentId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<String>> signWeeklyTask(@PathVariable("managerId") @Valid @Min(value = 1) Long managerId,
                                                              @PathVariable("studentId") @Valid @Min(value = 1) Long studentId,
                                                              @RequestBody @Valid WeeklySignUpdateJSON updateJSON){

        studentService.signWeekLog(studentId,managerId, LocalDate.parse(updateJSON.getStartDate()),LocalDate.parse(updateJSON.getEndDate()));
        APIResponseJSON<String> apiResponseJSON = new APIResponseJSON<>("Successfully signed");
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Search Managers ")
    @GetMapping(value = "search",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<PagedData<ManagerResponse>>> searchManager(@RequestParam(value = "email", required = false) String email,
                                                                                              @RequestParam(value = "state_name", required = false) String stateName,
                                                                                              @RequestParam(value = "phone", required = false) String phone,
                                                                                              @RequestParam(value = "size", required = false, defaultValue = "50") int size,
                                                                                              @RequestParam(value = "page", required = false, defaultValue = "0") int page){
        ManagerQueryRequest managerQueryRequest = ManagerQueryRequest.builder()
                .email(email)
                .stateName(stateName)
                .phone(phone)
                .build();
        PagedData<ManagerResponse> response =managerService.getManagers(managerQueryRequest,page,size);
        APIResponseJSON<PagedData<ManagerResponse>> apiResponseJSON = new APIResponseJSON<>("Successfully retrieved",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }


}
