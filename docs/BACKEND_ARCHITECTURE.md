# QuickCommerce Backend Architecture Documentation

## Overview

QuickCommerce is a modern e-commerce platform built using Spring Boot 3.x, following a layered architecture pattern. The backend is designed to be scalable, maintainable, and follows best practices for enterprise Java applications.

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA with Hibernate
- **Security**: Spring Security with JWT
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Maven
- **Java Version**: 17

## Architecture Layers

### 1. Presentation Layer (Controllers)

Located in `controller/` package, this layer handles HTTP requests and responses:

- `ProductController`: Manages product-related endpoints
- `CategoryController`: Handles category operations
- `CartController`: Manages shopping cart functionality
- `OrderController`: Handles order processing
- `UserController`: Manages user operations
- `DebugController`: Provides debugging endpoints

### 2. Service Layer

Located in `service/` package, contains business logic:

- `ProductService`: Product management logic
- `CategoryService`: Category management
- `CartService`: Shopping cart operations
- `OrderService`: Order processing logic
- `InventoryService`: Stock management
- `PredictionService`: Product recommendations
- `UserService`: User management

### 3. Repository Layer

Located in `repository/` package, handles data access:

- `ProductRepository`: Product data access
- `CategoryRepository`: Category data access
- `CartRepository`: Cart data access
- `OrderRepository`: Order data access
- `InventoryRepository`: Inventory data access
- `UserRepository`: User data access

### 4. Model Layer

Located in `model/` package, contains domain entities:

- `Product`: Product information
- `Category`: Product categories
- `Cart`: Shopping cart
- `Order`: Order information
- `Inventory`: Stock management
- `User`: User information
- `Review`: Product reviews

### 5. DTO Layer

Located in `dto/` package, contains Data Transfer Objects:

- Used for request/response data transfer
- Separates API contracts from domain models
- Provides data validation

### 6. Configuration Layer

Located in `config/` package:

- `SecurityConfig`: Security settings
- `CorsConfig`: CORS configuration
- `DataInitializer`: Initial data setup

## Database Schema

### Core Tables

1. **users**

   - User authentication and profile information
   - Fields: id, username, password, email, role

2. **categories**

   - Product categories
   - Fields: id, name, description, image_url

3. **products**

   - Product information
   - Fields: id, name, description, price, category_id, image_url

4. **inventory**

   - Stock management
   - Fields: id, product_id, current_stock, min_stock, max_stock

5. **cart_items**

   - Shopping cart contents
   - Fields: id, user_id, product_id, quantity

6. **orders**

   - Order information
   - Fields: id, user_id, order_date, status, total_amount

7. **order_items**
   - Individual items in orders
   - Fields: id, order_id, product_id, quantity, unit_price

## Key Features

### 1. Product Management

- Product listing with filtering and search
- Category-based organization
- Inventory tracking
- Product reviews and ratings

### 2. Shopping Cart

- Add/remove items
- Quantity management
- Price calculation
- Stock validation

### 3. Order Processing

- Order creation and management
- Status tracking
- Payment integration
- Order history

### 4. User Management

- User registration and authentication
- Role-based access control
- Profile management
- Address management

### 5. Inventory Management

- Real-time stock tracking
- Low stock alerts
- Stock updates
- Maximum stock limits

### 6. Recommendations

- Product recommendations based on:
  - Category similarity
  - User preferences
  - Purchase history
  - Popular items

## Security Features

1. JWT-based authentication
2. Role-based authorization
3. Password encryption
4. CORS protection
5. Input validation
6. SQL injection prevention

## API Endpoints

### Product APIs

- GET /api/products - List all products
- GET /api/products/{id} - Get product details
- GET /api/products/category/{categoryId} - Get products by category
- POST /api/products - Create new product
- PUT /api/products/{id} - Update product
- DELETE /api/products/{id} - Delete product

### Category APIs

- GET /api/categories - List all categories
- GET /api/categories/{id} - Get category details
- POST /api/categories - Create new category
- PUT /api/categories/{id} - Update category
- DELETE /api/categories/{id} - Delete category

### Cart APIs

- GET /api/cart - Get user's cart
- POST /api/cart/items - Add item to cart
- PUT /api/cart/items/{id} - Update cart item
- DELETE /api/cart/items/{id} - Remove item from cart

### Order APIs

- GET /api/orders - List user's orders
- POST /api/orders - Create new order
- GET /api/orders/{id} - Get order details
- PUT /api/orders/{id}/status - Update order status

## Data Flow

1. Client sends HTTP request to controller endpoint
2. Controller validates request and calls appropriate service
3. Service implements business logic
4. Service uses repository to access database
5. Repository executes database operations
6. Data flows back through layers to client

## Error Handling

- Global exception handling
- Custom exception classes
- Validation error responses
- HTTP status code mapping
- Detailed error messages

## Performance Considerations

1. Database indexing
2. Caching strategies
3. Pagination for large datasets
4. Optimized queries
5. Connection pooling

## Testing

1. Unit tests for services
2. Integration tests for controllers
3. Repository tests
4. Security tests
5. Performance tests

## Deployment

1. Docker containerization
2. Environment configuration
3. Database migration
4. Health monitoring
5. Logging and monitoring

## Future Enhancements

1. Microservices architecture
2. Caching layer
3. Message queue integration
4. Analytics dashboard
5. Advanced recommendation engine
6. Payment gateway integration
7. Email notifications
8. Mobile app support
