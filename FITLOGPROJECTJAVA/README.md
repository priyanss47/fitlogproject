# FitLog — Personal Workout & Calorie Tracker

A Spring Boot REST API for logging daily workouts and meals, and viewing weekly summaries of calories burned vs consumed.

---

## Tech Stack
- **Java 17**
- **Spring Boot 3.2**
- **Spring Data JPA**
- **MySQL**
- **Lombok**
- **Spring Validation**

---

## Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0+

---

## Setup Instructions

### 1. Create MySQL Database
Run the setup script in MySQL Workbench or MySQL CLI:
```sql
source src/main/resources/fitlog_db_setup.sql
```

### 2. Configure Database Credentials
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=root       # Your MySQL username
spring.datasource.password=root       # Your MySQL password
```

### 3. Build & Run
```bash
mvn clean install
mvn spring-boot:run
```

App runs on: **http://localhost:8080**

---

## API Endpoints

### Users
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users` | Create user |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Workouts
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/workouts` | Log a workout |
| GET | `/api/workouts` | Get all workouts |
| GET | `/api/workouts/{id}` | Get workout by ID |
| GET | `/api/workouts/user/{userId}?date=YYYY-MM-DD` | Get workouts by date |
| GET | `/api/workouts/user/{userId}/week?startDate=&endDate=` | Get weekly workouts |
| DELETE | `/api/workouts/{id}` | Delete workout |

### Meals
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/meals` | Log a meal |
| GET | `/api/meals` | Get all meals |
| GET | `/api/meals/{id}` | Get meal by ID |
| GET | `/api/meals/user/{userId}?date=YYYY-MM-DD` | Get meals by date |
| GET | `/api/meals/user/{userId}/week?startDate=&endDate=` | Get weekly meals |
| DELETE | `/api/meals/{id}` | Delete meal |

### Goals
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/goals` | Set weekly workout goal |
| GET | `/api/goals/user/{userId}` | Get goals by user |
| GET | `/api/goals/{id}` | Get goal by ID |
| DELETE | `/api/goals/{id}` | Delete goal |

### Summary
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/summary/daily/{userId}?date=YYYY-MM-DD` | Daily calorie summary |
| GET | `/api/summary/weekly/{userId}?weekStartDate=YYYY-MM-DD` | Weekly workout trend |

---

## Business Rules Enforced
1. Calories burnt and consumed must be **non-negative**
2. A daily/weekly summary is only generated when **at least one entry** exists
3. Only **one goal** can be set per user per week
4. Email addresses must be **unique** per user

---

## Testing with Postman
Import the Postman collection from `FitLog_Postman_Collection.json` (in project root).
