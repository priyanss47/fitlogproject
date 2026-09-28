package com.fitlog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MealDTO {

    @NotBlank(message = "Food item is required")
    private String foodItem;

    @NotBlank(message = "Quantity is required")
    private String quantity;

    @NotNull(message = "Calories is required")
    @Min(value = 0, message = "Calories must be non-negative")
    private Double calories;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "User ID is required")
    private Long userId;
}
