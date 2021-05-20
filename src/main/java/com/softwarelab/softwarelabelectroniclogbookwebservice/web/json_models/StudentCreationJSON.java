package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.NewUserRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.StudentCreationRequest;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
public class StudentCreationJSON {

    @ApiModelProperty(notes = "Student first name. Expected format: String", required = true)
    @JsonProperty(value = "first_name")
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String firstName;

    @ApiModelProperty(notes = "Student last name. Expected format: String", required = true)
    @JsonProperty(value = "last_name")
    @NotEmpty
    @NotNull
    @Size(max = 50)
    private String lastName;

    @ApiModelProperty(notes = "User email(as username). Expected format: valid email string", required = true)
    @NotEmpty
    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @ApiModelProperty(notes = "User phone number. Expected format: 11 digits", required = true)
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[0-9]{11}$",message = "Invalid phone number.")
    private String phone;

    @ApiModelProperty(notes = "User password. Expected format: String", required = true)
    @NotEmpty
    @NotNull
    @Size(max = 30, min = 6)
    private String password;

    @ApiModelProperty(notes = "Student Reg No. Expected format: String", required = true)
    @JsonProperty(value = "reg_number")
    @NotEmpty
    @NotNull
    private String regNo;

    @ApiModelProperty(notes = "Coordinator ID. Expected format: Long", required = true)
    @JsonProperty(value = "coordinator_id")
   @Min(value = 1)
    private Long coordinatorId;

    @ApiModelProperty(notes = "Manager ID. Expected format: Long", required = true)
    @JsonProperty(value = "manager_id")
    @Min(value = 1)
    private Long managerId;

    @JsonProperty(value = "start_date")
    @ApiModelProperty(notes = "Training start date. Expected format: yyyy-MM-dd", required = true)
    @NotNull
    private String startDate;

    public StudentCreationRequest getRequestModel(){
        return StudentCreationRequest.builder()
                .userRequest(getNewUser())
                .startDate(LocalDate.parse(startDate))
                .coordinatorId(coordinatorId)
                .managerId(managerId)
                .regNo(regNo)
                .build();
    }

    private NewUserRequest getNewUser(){
        return NewUserRequest.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .phone(phone)
                .build();
    }
}
