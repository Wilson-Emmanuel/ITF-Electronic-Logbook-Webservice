package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Data
@Builder
public class StudentQueryRequest {
    private String email;
    private String schoolName;
    private String stateName;
    private String managerEmail;
    private String coordinatorEmail;
    private Boolean paid;
    private Boolean logBookSigned;
}
