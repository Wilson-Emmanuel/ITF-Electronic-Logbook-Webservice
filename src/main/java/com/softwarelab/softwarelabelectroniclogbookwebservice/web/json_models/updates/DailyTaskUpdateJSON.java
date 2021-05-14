package com.softwarelab.softwarelabelectroniclogbookwebservice.web.json_models.updates;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by Wilson
 * on Tue, 11/05/2021.
 */
@Data
public class DailyTaskUpdateJSON {

    @NotEmpty
    @NotNull
    private String task;

    @NotNull
    @NotEmpty
    private String taskDate;
}
