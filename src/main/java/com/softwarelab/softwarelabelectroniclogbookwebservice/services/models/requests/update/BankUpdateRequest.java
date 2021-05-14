package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.requests.update;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Wilson
 * on Mon, 10/05/2021.
 */
@Data
@Builder
public class BankUpdateRequest {
    private String accountNumber;
    private String accountName;
    private String bankName;
    private String sortCode;
}
