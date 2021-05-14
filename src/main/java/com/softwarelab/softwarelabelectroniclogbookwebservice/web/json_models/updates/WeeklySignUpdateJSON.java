package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Data
public class WeeklySignUpdateJSON {
    @JsonProperty(value = "start_date")
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //@JsonFormat(pattern = "MM/dd/yyyy")
    @NotNull
    private String startDate;

    @JsonProperty(value = "end_date")
    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //@JsonFormat(pattern = "MM/dd/yyyy")
    @NotNull
    private String endDate;
}
