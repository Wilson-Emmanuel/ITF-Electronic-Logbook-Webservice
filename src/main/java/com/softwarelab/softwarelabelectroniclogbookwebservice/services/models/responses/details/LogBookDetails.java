package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class LogBookDetails {
    @JsonProperty(value = "signed")
    private boolean signed;

    @JsonProperty(value = "weeks")
    private List<WeeklyTaskDetails> weeklyTaskDetails;

}
