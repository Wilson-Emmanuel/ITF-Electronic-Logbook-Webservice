package com.softwarelab.softwarelabelectroniclogbookwebservice.services.models.responses.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wilson
 * on Sun, 09/05/2021.
 */
@Data
@Builder
public class WeeklyTaskDetails {
    @JsonProperty(value = "week_number")
    private int weekNo;

    @JsonProperty(value = "signed")
    private boolean signed;

    @Builder.Default
    @JsonProperty(value = "days")
    List<DailyTaskDetails> dailyTasks = new ArrayList<>();
}
