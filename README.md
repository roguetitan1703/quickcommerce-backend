# Snap Quick Commerce Backend

The backend service for Snap Quick Commerce, providing API endpoints for e-commerce functionality and predictive inventory management.

## Features

- **User Authentication**: Secure login and registration
- **Product Management**: CRUD operations for products
- **Inventory Management**: Track product inventory levels
- **Order Processing**: Handle customer orders
- **Cart Management**: Shopping cart functionality
- **Predictions & Recommendations**: AI-powered product recommendations and inventory predictions

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- MySQL database

### Installation

1. Clone the repository

   ```
   git clone [repository-url]
   ```

2. Configure the database connection in `application.properties`

   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/quickcommerce
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

3. Build the project

   ```
   mvn clean install
   ```

4. Run the application
   ```
   mvn spring-boot:run
   ```

The server will start on http://localhost:8080

## API Documentation

We provide multiple ways to access our API documentation:

1. **Interactive HTML Documentation**: Access our user-friendly documentation by visiting http://localhost:8080/docs when the application is running.

2. **Swagger UI**: For interactive API testing, access Swagger UI at http://localhost:8080/swagger-ui.html

3. **Postman Collection**: Import our Postman collection from the `/docs` directory for easy API testing.

## Project Structure

```
├── src/
│   ├── main/
│   │   ├── java/com/example/quickcommerce/
│   │   │   ├── config/       # Configuration classes
│   │   │   ├── controller/   # REST controllers
│   │   │   ├── model/        # Domain models
│   │   │   ├── repository/   # Data access layer
│   │   │   ├── service/      # Business logic
│   │   │   ├── util/         # Utility classes
│   │   │   └── QuickCommerceApplication.java
│   │   └── resources/
│   │       ├── static/       # Static resources
│   │       ├── templates/    # View templates
│   │       └── application.properties
│   └── test/                 # Unit and integration tests
├── docs/                     # API documentation
└── pom.xml
```

## Technologies

- **Spring Boot**: Backend framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Data access layer
- **Swagger/OpenAPI**: API documentation
- **MySQL**: Database
- **Maven**: Dependency management
- **JWT**: JSON Web Tokens for authentication

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Database Configuration

The application can be run with either:

1. **H2 Database** (development/testing): In-memory database that doesn't require any setup
2. **PostgreSQL** (production): Persistent relational database for production use

### Setting up PostgreSQL

For production use, the application is configured to use PostgreSQL.

1. Install PostgreSQL from the [official website](https://www.postgresql.org/download/)
2. Create a database for the application:
   ```sql
   CREATE DATABASE snapquickcommerce;
   ```
3. The default configuration uses:
   - URL: `jdbc:postgresql://localhost:5432/snapquickcommerce`
   - Username: `postgres`
   - Password: `postgres`

You can modify these settings in `application-prod.properties`.

See the detailed [PostgreSQL Setup Guide](docs/DATABASE_SETUP.md) for more information.

### Running with Different Database Profiles

- Development mode (H2):
  ```
  ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
  ```
- Production mode (PostgreSQL):
  ```
  ./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
  ```
