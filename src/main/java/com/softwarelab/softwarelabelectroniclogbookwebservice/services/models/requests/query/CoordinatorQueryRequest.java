package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class CoordinatorQueryRequest {
    private String email;
    private String phone;
    private String schoolName;
    private String state;
}
