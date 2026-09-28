package com.fitlog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GoalDTO {

    @NotNull(message = "Week start date is required")
    private LocalDate weekStartDate;

    @NotNull(message = "Weekly workout count goal is required")
    @Min(value = 1, message = "Weekly workout goal must be at least 1")
    private Integer weeklyWorkoutCountGoal;

    @NotNull(message = "User ID is required")
    private Long userId;
}
