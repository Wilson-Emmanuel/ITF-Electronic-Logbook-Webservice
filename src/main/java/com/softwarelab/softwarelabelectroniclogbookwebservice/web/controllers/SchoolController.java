package com.softwarelab.softwarelabelectroniclogbookwebservice.web.controllers;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.SchoolQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.APIResponseJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.SchoolResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.SchoolService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.SchoolCreationJSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Validated
@Api(tags = "Handles school management")
@RestController
@RequestMapping(value = "/v1/school/", headers = {"Authorization", "x-client-request-key"})
public class SchoolController {

    SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

   @ApiOperation(value = "Add new school", notes = "")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<SchoolResponse>> addSchool(@RequestBody @Valid SchoolCreationJSON creationJSON){

        SchoolResponse response = schoolService.saveSchool(creationJSON.getRequestModel());
        APIResponseJSON<SchoolResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Update an existing school", notes = "")
    @PutMapping(value = "{schoolId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<SchoolResponse>> updateSchoolInfo(@PathVariable("schoolId") @Valid @Min(value = 1) Integer schoolId,
                                                                            @RequestBody @Valid SchoolCreationJSON updateJson){

        SchoolResponse response = schoolService.updateSchool(updateJson.getUpdateRequest(),schoolId);
        APIResponseJSON<SchoolResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Get an existing School by Id", notes = "")
    @GetMapping(value = "{schoolId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<SchoolResponse>> getSchool(@PathVariable("schoolId") @Valid @Min(value = 1) Integer schoolId){

        SchoolResponse response = schoolService.getSchoolById(schoolId);
        APIResponseJSON<SchoolResponse> apiResponseJSON = new APIResponseJSON<>("Successfully retrieved",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }
    @ApiOperation(value = "Get an existing School by Name ", notes = "School names are unique")
    @GetMapping(value = "{schoolName}/by-name",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<SchoolResponse>> fetchSchoolByName(@PathVariable("schoolName") @Valid @NotEmpty String schoolName){

        SchoolResponse response = schoolService.getSchoolByName(schoolName);
        APIResponseJSON<SchoolResponse> apiResponseJSON = new APIResponseJSON<>("Successfully retrieved",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Search schools ")
    @GetMapping(value = "search",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<PagedData<SchoolResponse>>> searchSchools(@RequestParam(value = "school_name", required = false) String schoolName,
                                                                                        @RequestParam(value = "state_name", required = false) String stateName,
                                                                                        @RequestParam(value = "size", required = false, defaultValue = "50") int size,
                                                                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page){
        SchoolQueryRequest schoolQueryRequest = SchoolQueryRequest.builder()
                .schoolName(schoolName)
                .stateName(stateName)
                .build();
        PagedData<SchoolResponse> response = schoolService.getSchools(schoolQueryRequest,page,size);
        APIResponseJSON<PagedData<SchoolResponse>> apiResponseJSON = new APIResponseJSON<>("Successfully retrieved",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }



}
