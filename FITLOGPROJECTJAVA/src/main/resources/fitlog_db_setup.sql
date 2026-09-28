-- ============================================================
-- FitLog Database Setup Script
-- Run this in MySQL Workbench or MySQL CLI before starting app
-- ============================================================

-- Create the database
CREATE DATABASE IF NOT EXISTS fitlog_db;
USE fitlog_db;

-- ============================================================
-- The tables below are AUTO-CREATED by Hibernate (ddl-auto=update)
-- This script shows the expected schema for reference
-- ============================================================

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    created_at  DATETIME
);

-- Workouts Table
CREATE TABLE IF NOT EXISTS workouts (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    type            VARCHAR(255) NOT NULL,
    duration_mins   INT NOT NULL,
    calories_burnt  DOUBLE NOT NULL,
    date            DATE NOT NULL,
    user_id         BIGINT NOT NULL,
    CONSTRAINT fk_workout_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Meals Table
CREATE TABLE IF NOT EXISTS meals (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    food_item   VARCHAR(255) NOT NULL,
    quantity    VARCHAR(255) NOT NULL,
    calories    DOUBLE NOT NULL,
    date        DATE NOT NULL,
    user_id     BIGINT NOT NULL,
    CONSTRAINT fk_meal_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Goals Table
CREATE TABLE IF NOT EXISTS goals (
    id                          BIGINT AUTO_INCREMENT PRIMARY KEY,
    week_start_date             DATE NOT NULL,
    weekly_workout_count_goal   INT NOT NULL,
    user_id                     BIGINT NOT NULL,
    CONSTRAINT fk_goal_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================================
-- Sample Data (Optional - for testing)
-- ============================================================

-- Insert a sample user
INSERT INTO users (name, email, created_at) VALUES
    ('Priya Sharma', 'priya@fitlog.com', NOW()),
    ('Rahul Verma',  'rahul@fitlog.com', NOW());

-- Insert sample workouts
INSERT INTO workouts (type, duration_mins, calories_burnt, date, user_id) VALUES
    ('Running',  30, 300.0, '2024-01-15', 1),
    ('Cycling',  45, 400.0, '2024-01-16', 1),
    ('Swimming', 60, 500.0, '2024-01-17', 1);

-- Insert sample meals
INSERT INTO meals (food_item, quantity, calories, date, user_id) VALUES
    ('Oatmeal',      '1 bowl',  350.0, '2024-01-15', 1),
    ('Chicken Rice', '1 plate', 600.0, '2024-01-15', 1),
    ('Salad',        '1 bowl',  150.0, '2024-01-15', 1);

-- Insert sample goal
INSERT INTO goals (week_start_date, weekly_workout_count_goal, user_id) VALUES
    ('2024-01-15', 5, 1);

SELECT 'FitLog database setup complete!' AS message;
