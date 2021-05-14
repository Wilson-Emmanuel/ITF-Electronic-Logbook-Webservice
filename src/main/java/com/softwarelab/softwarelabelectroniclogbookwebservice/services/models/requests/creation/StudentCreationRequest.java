package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.creation;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Data
@Builder
public class StudentCreationRequest {
    private NewUserRequest userRequest;
    private String regNo;
    private Long coordinatorId;
    private Long managerId;//manager should be created first
    private LocalDate startDate;
}
