package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.SchoolEntity;
import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StateEntity;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class CoordinatorDTO {
    private String email;
    private String phone;
    private SchoolEntity school;
    private StateEntity state;
}
