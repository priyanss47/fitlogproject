package com.fitlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "meals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Food item is required")
    @Column(name = "food_item", nullable = false)
    private String foodItem;

    @NotBlank(message = "Quantity is required")
    @Column(nullable = false)
    private String quantity;

    @NotNull(message = "Calories is required")
    @Min(value = 0, message = "Calories must be non-negative")
    @Column(nullable = false)
    private Double calories;

    @NotNull(message = "Date is required")
    @Column(nullable = false)
    private LocalDate date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
