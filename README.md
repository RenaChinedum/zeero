# Overview
This To-do app is a simple task management application that allows users to create, update, and manage their daily tasks efficiently. 

# Features
- User authentication using JWT (JSON Web Tokens).
- Manage to-do items: Create, Read, Update, and Delete tasks.
- Manage app user: sign up, sign, update profile.
- Caching with In-Memory Cache for faster task retrieval.
- **Mockito:** used for Unit Testing of services and controllers.
- Spring Boot integration for rapid development.
- RESTful API architecture.
- Swagger documentation

# Technologies Used
- **Spring Boot:** For building the application.
- **Spring Security with JWT:** For securing the app with token-based authentication.
- **Spring Data JPA:** For database interactions.
- **H2 Database:** In-memory database for fast and simple development.
- **In-Memory Cache:** Used to cache frequently accessed data for better performance.
- **Mockito:** For unit testing.
- **Lombok:** To reduce boilerplate code.
- **JUnit:** For writing and running tests.

# Setup Instructions

## Prerequisites
**Java 17+**
**Maven 3.6+**
- Postman (for testing the API endpoints)
- Swagger (for testing the API endpoints)

## Steps to Run the Application

### 1. Clone the repository:

git clone https://github.com/your-repo/todo-app.git

### 2. Build the application:
 mvn clean install

### 3. Run the application:
mvn spring-boot:run

### 4. The application should now be running at http://localhost:8089.

# Running Tests
Run unit tests using Mockito and JUnit:
mvn test

# Cache Configuration
The application uses an in-memory cache to store frequently accessed data, reducing the number of database queries for better performance. Caching can be configured in the application.properties or directly in the service layer using annotations.

# Security
Authentication is implemented using JWT. After logging in, the client receives a token, which is passed with every request in the Authorization header as Bearer <token>.

# Api Docummentaion
The api docummetation was done using SpringFox library for swagger configuration, which reveals every endpoint and their respective request as well as response.


# Logging
Logging is implemented using Spring Slfj4. The logs are written to the console and can be configured in the application.properties file.

# Configuration
The application can be configured using the application.properties file. You can set the database connection details, caching configurations, and other settings.
