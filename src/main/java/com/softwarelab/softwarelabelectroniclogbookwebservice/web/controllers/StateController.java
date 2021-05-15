package com.softwarelab.softwarelabelectroniclogbookwebservice.web.controllers;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.SchoolQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.APIResponseJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.SchoolResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.StateResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.SchoolService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.StateService;
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
import java.util.List;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Api(tags = "Fetch all states")
@RestController
@RequestMapping(value = "/v1/states")
public class StateController {

   StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @ApiOperation(value = "Fetch all states", notes = "")
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<List<StateResponse>>> getAllStates(){
        List<StateResponse> stateResponses = stateService.getAllStates();
        APIResponseJSON<List<StateResponse>> apiResponseJSON = new APIResponseJSON<>("Successfully processed",stateResponses);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }



}
