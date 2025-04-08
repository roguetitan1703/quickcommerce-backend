# QuickCommerce Backend Service

[![Language](https://img.shields.io/badge/Language-Java%2017-blue.svg)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Framework](https://img.shields.io/badge/Framework-Spring%20Boot%203.2-green.svg)](https://spring.io/projects/spring-boot)
[![Database](https://img.shields.io/badge/Database-PostgreSQL-blue.svg)](https://www.postgresql.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) <!-- Replace with actual license badge -->

Core backend API service for the **QuickCommerce** platform, built with Java & Spring Boot. It provides RESTful endpoints, manages business logic, interacts with the PostgreSQL database, handles authentication, and communicates with other platform services.

## ✨ Core Features

*   🔐 **Authentication & Authorization:** Secure JWT-based login/registration via Spring Security. Role-based access (User, Admin).
*   📦 **Product & Category Management:** Full CRUD operations.
*   📊 **Inventory Tracking:** Real-time stock levels, updates, and low-stock alerts.
*   🛒 **Shopping Cart:** User-specific persistent cart functionality.
*   🧾 **Order Processing:** Order creation, status updates, and history.
*   🔗 **API Orchestration:** Central hub communicating with frontends and ML microservices.
*   💾 **Data Persistence:** Spring Data JPA with Hibernate ORM for PostgreSQL.

## 🛠 Technology Stack

| Category      | Technology / Library        |
| :------------ | :-------------------------- |
| **Language**  | Java 17                     |
| **Framework** | Spring Boot 3.2.0           |
| **Database**  | PostgreSQL                  |
| **Data Access**| Spring Data JPA, Hibernate |
| **Security**  | Spring Security, JWT        |
| **API Docs**  | SpringDoc OpenAPI (Swagger) |
| **Build Tool**| Apache Maven                |

## 📋 Prerequisites

*   **Java Development Kit (JDK):** Version 17 or later.
*   **Apache Maven:** Version 3.6 or later.
*   **PostgreSQL Server:** Running instance accessible. See [Database Setup Guide](docs/DATABASE_SETUP.md).

## 🚀 Getting Started

1.  **Clone the repository:**
    bash
    git clone [URL_OF_THIS_BACKEND_REPO]
    cd quickcommerce-backend
    
2.  **Database Setup:**
    *   Ensure your PostgreSQL server is running.
    *   Follow the instructions in [`docs/DATABASE_SETUP.md`](docs/DATABASE_SETUP.md) to create the `snapquickcommerce` database and user (if necessary).

3.  **Configure Database Connection:**
    *   Review and update connection details in `src/main/resources/application-prod.properties`. Defaults assume `localhost`, database `snapquickcommerce`, user `postgres`, password `root`. Adjust as needed for your setup.
    properties
    # src/main/resources/application-prod.properties
    spring.datasource.url=jdbc:postgresql://<YOUR_DB_HOST>:<YOUR_DB_PORT>/snapquickcommerce
    spring.datasource.username=<YOUR_DB_USER>
    spring.datasource.password=<YOUR_DB_PASSWORD>
    spring.jpa.hibernate.ddl-auto=update # Recommended for prod after initial setup
    spring.sql.init.mode=always # Change to 'never' after first run with sample data
    

4.  **Build the Project:**
    Compile the code and download dependencies using Maven.
    bash
    mvn clean install
    

5.  **Run the Application:**
    Choose the appropriate profile:
    *   **Production Mode (using PostgreSQL):**
        bash
        # Uses application-prod.properties
        mvn spring-boot:run -Dspring-boot.run.profiles=prod
        
    *   **Development Mode (using H2 In-Memory DB):**
        bash
        # Uses application-dev.properties (requires H2 dependency in pom.xml)
        mvn spring-boot:run -Dspring-boot.run.profiles=dev
        

    > The application will start by default on `http://localhost:8080`.

## 📄 API Documentation

Explore the API endpoints using the integrated Swagger UI:

*   **Swagger UI:** `http://localhost:8080/swagger-ui.html`
*   **OpenAPI Spec (JSON):** `http://localhost:8080/v3/api-docs`

For a detailed overview of all endpoints, request/response formats, and authentication requirements, please refer to the [`docs/API_DOCUMENTATION.md`](docs/API_DOCUMENTATION.md) file.

## 🏗 Project Structure

plaintext
quickcommerce-backend/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/com/example/quickcommerce/  # Core Java code
│   │   │   ├── config/       # Spring Config (Security, CORS)
│   │   │   ├── controller/   # REST Controllers
│   │   │   ├── dto/          # Data Transfer Objects
│   │   │   ├── exception/    # Custom Exceptions & Handlers
│   │   │   ├── model/        # JPA Entities
│   │   │   ├── repository/   # Data Repositories
│   │   │   ├── service/      # Business Logic Services
│   │   │   └── QuickCommerceApplication.java # Entry Point
│   │   └── 📁 resources/
│   │       ├── application.properties        # Base configuration
│   │       ├── application-dev.properties    # Dev profile overrides (H2)
│   │       ├── application-prod.properties   # Prod profile overrides (PostgreSQL)
│   │       └── data.sql                      # Initial sample data SQL
│   └── 📁 test/                               # Unit & Integration Tests
├── 📁 docs/                                 # Detailed Documentation
│   ├── API_DOCUMENTATION.md
│   └── DATABASE_SETUP.md
├── 📁 scripts/                              # Utility Scripts (e.g., DB setup)
│   └── setup-postgres.bat                  # Windows script example
└── pom.xml                                   # Maven Project Configuration


## 🧪 Testing

Execute the test suite using Maven:

bash
mvn test

