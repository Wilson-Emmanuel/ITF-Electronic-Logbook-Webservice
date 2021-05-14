package com.softwarelab.softwarelabelectroniclogbookwebservice.web.controllers;

import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query.StudentQueryRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.APIResponseJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.PagedData;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.StudentResponse;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details.BankDetails;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.usecase.StudentService;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.exceptions.DataValidationException;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.StudentCreationJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.BankUpdateJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.DailyTaskUpdateJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.StudentUpdateJSON;
import com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates.UpdatePassword;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@Api(tags = "Handles operation of Students")
@RestController
@RequestMapping(value = "/v1/student/", headers = {"Authorization", "x-client-request-key"})
public class StudentController {

    StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation(value = "Creates new Student", notes = "")
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<StudentResponse>> createStudent(@RequestBody @Valid StudentCreationJSON creationJSON){

        StudentResponse response = studentService.saveStudent(creationJSON.getRequestModel());
        APIResponseJSON<StudentResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Update an existing student", notes = "")
    @PutMapping(value = "{studentId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<StudentResponse>> updateStudentInfo(@PathVariable("studentId") @Valid @Min(value = 1) Long studentId,
                                                                            @RequestBody @Valid StudentUpdateJSON creationJSON){

        StudentResponse response = studentService.updateStudent(creationJSON.getRequestModel(),studentId);
        APIResponseJSON<StudentResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Get an existing Student", notes = "")
    @GetMapping(value = "{studentId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<StudentResponse>> fetchStudent(@PathVariable("studentId") @Valid @Min(value = 1) Long studentId){

        StudentResponse response = studentService.getUserById(studentId);
        APIResponseJSON<StudentResponse> apiResponseJSON = new APIResponseJSON<>("Successfully retrieved",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

   @ApiOperation(value = "Update Student Password", notes = "")
    @PutMapping(value = "/change-password/{studentId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<StudentResponse>> updateStudentPassword(@PathVariable("studentId") @Valid @Min(value = 1) Long studentId,
                                                                            @RequestBody @Valid UpdatePassword updatePassword){

        StudentResponse response = studentService.updateUserPassword(updatePassword.getPassword(), studentId);
        APIResponseJSON<StudentResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Update Student Bank Account Details", notes = "")
    @PutMapping(value = "/update-bank/{studentId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<BankDetails>> updateStudentBankDetails(@PathVariable("studentId") @Valid @Min(value = 1) Long studentId,
                                                                                 @RequestBody @Valid BankUpdateJSON updateJSON){

        BankDetails response = studentService.updateBank(updateJSON.getRequestModel(), studentId);
        APIResponseJSON<BankDetails> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Update/Fill daily task/logbook", notes = "")
    @PutMapping(value = "/fill-logbook/{studentId}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<StudentResponse>> updateTask(@PathVariable("studentId") @Valid @Min(value = 1) Long studentId,
                                                                                 @RequestBody @Valid DailyTaskUpdateJSON updateJSON){

        StudentResponse response = studentService.updateTask(studentId,updateJSON.getTask(), LocalDate.parse(updateJSON.getTaskDate()));
        APIResponseJSON<StudentResponse> apiResponseJSON = new APIResponseJSON<>("Successfully processed",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

    @ApiOperation(value = "Search Students ")
    @GetMapping(value = "search",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseJSON<PagedData<StudentResponse>>> searchStudents(@RequestParam(value = "student_email", required = false) String email,
                                                                                     @RequestParam(value = "coordinator_email", required = false) String coordinatorEmail,
                                                                                     @RequestParam(value = "manager_email", required = false) String managerEmail,
                                                                                     @RequestParam(value = "state_name", required = false) String stateName,
                                                                                     @RequestParam(value = "school_name", required = false) String schoolName,
                                                                                      @RequestParam(value = "logbook_signed", required = false) String logBookSigned,
                                                                                      @RequestParam(value = "paid", required = false) String paid,
                                                                                     @RequestParam(value = "size", required = false, defaultValue = "20") int size,
                                                                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page){
        StudentQueryRequest studentQueryRequest = StudentQueryRequest.builder()
                .email(email)
                .schoolName(schoolName)
                .stateName(stateName)
                .managerEmail(managerEmail)
                .coordinatorEmail(coordinatorEmail)
                .build();
        if(StringUtils.isNotBlank(logBookSigned) && !logBookSigned.equalsIgnoreCase("yes") && !logBookSigned.equals("no")){
            throw new DataValidationException("badly formatted logbook_signed");
        }
        if(StringUtils.isNotBlank(paid) && !paid.equalsIgnoreCase("yes") && !paid.equals("no")){
            throw new DataValidationException("badly formatted paid parameter");
        }
        if(StringUtils.isNotBlank(logBookSigned))
            studentQueryRequest.setLogBookSigned(logBookSigned.equalsIgnoreCase("yes")?Boolean.TRUE:Boolean.FALSE);

        if(StringUtils.isNotBlank(paid))
            studentQueryRequest.setPaid(paid.equalsIgnoreCase("yes")?Boolean.TRUE:Boolean.FALSE);

        PagedData<StudentResponse> response =studentService.getStudents(studentQueryRequest,page,size);
        APIResponseJSON<PagedData<StudentResponse>> apiResponseJSON = new APIResponseJSON<>("Successfully retrieved",response);
        return new ResponseEntity<>(apiResponseJSON, HttpStatus.OK);
    }

}
