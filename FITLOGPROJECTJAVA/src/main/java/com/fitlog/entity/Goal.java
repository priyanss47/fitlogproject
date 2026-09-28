package com.fitlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "goals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Week start date is required")
    @Column(name = "week_start_date", nullable = false)
    private LocalDate weekStartDate;

    @NotNull(message = "Weekly workout goal is required")
    @Min(value = 1, message = "Weekly workout goal must be at least 1")
    @Column(name = "weekly_workout_count_goal", nullable = false)
    private Integer weeklyWorkoutCountGoal;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
