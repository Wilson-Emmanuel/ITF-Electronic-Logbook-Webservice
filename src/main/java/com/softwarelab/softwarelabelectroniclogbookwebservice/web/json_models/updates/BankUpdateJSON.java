package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update.BankUpdateRequest;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Data
public class BankUpdateJSON {
    @JsonProperty(value = "account_number")
    @NotEmpty
    @NotNull
    @Pattern(regexp = "^[0-9]{10}$",message = "Invalid account number.")
    private String accountNumber;

    @JsonProperty(value = "account_name")
    @NotEmpty
    @NotNull
    private String accountName;

    @JsonProperty(value = "bank_name")
    @NotEmpty
    @NotNull
    private String bankName;

    @JsonProperty(value = "sort_code")
    @NotEmpty
    @NotNull
    private String sortCode;

    public BankUpdateRequest getRequestModel(){
        return BankUpdateRequest.builder()
                .accountName(accountName)
                .bankName(bankName)
                .sortCode(sortCode)
                .accountNumber(accountNumber)
                .build();
    }
}
