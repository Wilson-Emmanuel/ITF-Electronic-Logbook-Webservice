package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.dto;

import com.softwarelab.softwarelabelectroniclogbookwebservice.persistence.entities.StateEntity;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Data
@Builder
public class ManagerDTO {
    private String email;
    private String phone;
    private StateEntity state;
}
