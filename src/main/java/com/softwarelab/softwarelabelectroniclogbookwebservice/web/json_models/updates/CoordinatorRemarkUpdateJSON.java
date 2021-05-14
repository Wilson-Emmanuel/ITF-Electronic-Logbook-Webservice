package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Data
public class CoordinatorRemarkUpdateJSON {

    @JsonProperty(value = "coordinator_remark")
    @NotEmpty
    @NotNull
    private String coordinatorRemark;
}
