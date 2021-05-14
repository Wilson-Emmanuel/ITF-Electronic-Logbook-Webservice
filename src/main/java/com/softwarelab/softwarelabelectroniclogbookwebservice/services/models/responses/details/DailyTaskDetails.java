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
public class DailyTaskDetails {
    @JsonProperty(value = "date")
    private String date;

    @JsonProperty(value = "task")
    private String task;

    @Builder.Default
    @JsonProperty(value = "editable")
    private boolean editable = false;
}
