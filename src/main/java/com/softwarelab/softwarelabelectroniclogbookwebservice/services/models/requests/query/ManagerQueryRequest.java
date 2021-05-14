package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.query;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Data
@Builder
public class ManagerQueryRequest {
     private String email;
     private String phone;
     private String stateName;
}
