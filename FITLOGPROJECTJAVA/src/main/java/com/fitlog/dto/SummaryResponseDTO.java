package com.fitlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponseDTO {

    // Daily Summary Fields
    private LocalDate date;
    private Double totalCaloriesBurnt;
    private Double totalCaloriesConsumed;
    private Double calorieBalance; // consumed - burnt (negative = deficit)

    // Weekly Summary Fields
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private Long totalWorkoutsCompleted;
    private Integer weeklyGoal;
    private String goalStatus; // "ACHIEVED", "IN_PROGRESS", "NO_GOAL_SET"
}
