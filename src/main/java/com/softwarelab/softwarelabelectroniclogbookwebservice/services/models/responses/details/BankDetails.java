package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class BankDetails {
    @JsonProperty(value =" account_name")
    private String accountName;

    @JsonProperty(value = "account_number")
    private String accountNumber;

    @JsonProperty(value = "bank_name")
    private String bankName;

    @JsonProperty(value = "sort_code")
    private String bankSortCode;
}
