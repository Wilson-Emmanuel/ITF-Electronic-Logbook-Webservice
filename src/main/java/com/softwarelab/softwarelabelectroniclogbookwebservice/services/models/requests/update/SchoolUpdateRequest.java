package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class SchoolUpdateRequest {
    private String schoolName;
    private String stateName;
    private String address;
}
