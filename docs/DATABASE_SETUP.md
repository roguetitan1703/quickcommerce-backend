# PostgreSQL Database Setup Guide

This guide explains how to set up PostgreSQL for the Snap Quick Commerce application.

## Prerequisites

1. [PostgreSQL](https://www.postgresql.org/download/) installed on your system
2. Basic knowledge of SQL commands

## Quick Setup (Windows)

We've provided a setup script for Windows users that automates the database creation process:

1. Ensure PostgreSQL is installed and added to your system PATH
2. Open Command Prompt
3. Navigate to the project directory
4. Run the setup script:
   ```
   cd quickcommerce-backend
   scripts\setup-postgres.bat
   ```
5. Follow the prompts to enter your PostgreSQL credentials
6. Once complete, you can run the application with the production profile

## Manual Installation

### Windows

1. Download PostgreSQL installer from the [official website](https://www.postgresql.org/download/windows/)
2. Run the installer and follow the instructions
3. Remember the password you set for the `postgres` user
4. Install pgAdmin (included in the installer) for easier database management

### macOS

Using Homebrew:

```shell
brew install postgresql
brew services start postgresql
```

### Linux (Ubuntu/Debian)

```shell
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

## Database Setup

1. Open a terminal/command prompt
2. Connect to PostgreSQL:

   ```shell
   # For Windows
   psql -U postgres

   # For macOS/Linux
   sudo -u postgres psql
   ```

3. Create a database for the application:

   ```sql
   CREATE DATABASE snapquickcommerce;
   ```

4. (Optional) Create a dedicated user:

   ```sql
   CREATE USER snapuser WITH ENCRYPTED PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE snapquickcommerce TO snapuser;
   ```

5. Exit PostgreSQL:

   ```sql
   \q
   ```

## Configuration in Application

The application is already configured to connect to a PostgreSQL database with the following properties in `application.properties` or `application-prod.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/snapquickcommerce
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

Update these properties if you created a different database name or user.

## Sample Data

The application includes sample data that will be automatically loaded when you run the application with the production profile for the first time. This includes:

- User accounts (admin/user)
- 8 product categories:
  - Dairy
  - Fruits & Vegetables
  - Bakery
  - Snacks
  - Beverages
  - Household
  - Personal Care
  - Frozen Foods
- 46 products across all categories
- Inventory levels for all products

After initial data load, you can set `spring.sql.init.mode=never` in application-prod.properties to prevent reloading the sample data on each startup.

## Running the Application with PostgreSQL

To run the application with PostgreSQL:

```shell
# Run with production profile (using PostgreSQL)
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod

# Using Maven directly
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## Switching Between Development and Production

The application has multiple profiles:

- `dev`: Uses H2 in-memory database (for development)
- `prod`: Uses PostgreSQL database (for production)

Switch between profiles by setting the `spring.profiles.active` property:

```shell
# Development mode with H2
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Production mode with PostgreSQL
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

## Database Schema

The application uses JPA/Hibernate with `spring.jpa.hibernate.ddl-auto=update` in production, which automatically updates the schema based on entity classes. In development mode, it uses `create-drop` to recreate the schema every time the application starts.

For production environments, consider using a database migration tool like Flyway or Liquibase for more controlled schema updates.
