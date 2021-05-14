package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.NewUserRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation.StudentCreationRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.StudentUpdateRequest;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.UserUpdateRequest;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
public class StudentUpdateJSON {

    @NotEmpty
    @NotNull
    @Size(max = 255)
    @Email
    private String email;

    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[0-9]{11}$",message = "Invalid phone number.")
    private String phone;


    @JsonProperty(value = "coordinator_id")
   @Min(value = 0)
    private Long coordinatorId;

    @JsonProperty(value = "manager_id")
    @Min(value = 0)
    private Long managerId;

     public StudentUpdateRequest getRequestModel(){
         return StudentUpdateRequest.builder()
                 .managerId(managerId)
                 .coordinatorId(coordinatorId)
                 .updateRequest(getUser())
                 .build();
     }

    private UserUpdateRequest getUser(){
        return UserUpdateRequest.builder()
                .email(email)
                .phone(phone)
                .build();
    }
}
