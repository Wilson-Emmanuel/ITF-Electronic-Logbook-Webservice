package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.CoordinatorEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.ManagerEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.SchoolEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StateEntity;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Data
@Builder
public class StudentDTO {
    private String email;
    private SchoolEntity school;
    private StateEntity state;
    private ManagerEntity manager;
    private CoordinatorEntity coordinator;
    private Boolean paid;
    private Boolean logBookSigned;
}
