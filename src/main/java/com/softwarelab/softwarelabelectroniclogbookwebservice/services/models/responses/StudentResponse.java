package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details.*;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class StudentResponse {
    @JsonProperty(value = "student_id")
    private Long id;

    @JsonProperty(value="reg_number")
    private String regNo;

    @JsonProperty(value = "personal_info")
    private AppUserDetails personalDetail;

    @JsonProperty(value = "school")
    private SchoolDetails schoolDetails;

    @JsonProperty(value = "bank")
    private BankDetails bank;

    @JsonProperty(value = "company")
    private CompanyDetails companyDetails;

    @JsonProperty(value = "start_date")
    private String startDate;

    @JsonProperty(value = "log_book")
    private LogBookDetails logBook;

    @JsonProperty(value = "paid")
    private boolean paid;
}
