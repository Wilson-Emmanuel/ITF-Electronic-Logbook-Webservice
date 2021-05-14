package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StateEntity;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class SchoolDTO {
    private String schoolName;
    private StateEntity state;
}
