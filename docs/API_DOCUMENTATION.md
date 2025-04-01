# Snap Quick Commerce API Documentation

This document provides detailed information about the API endpoints available in the Snap Quick Commerce backend.

## Base URL

```
http://localhost:8080/api
```

## Authentication

Most endpoints require authentication. To authenticate, include the JWT token in the Authorization header:

```
Authorization: Bearer <token>
```

To obtain a token, use the login endpoint.

---

## Endpoints

### Authentication

#### Login

Authenticates a user and returns a JWT token.

- **URL**: `/auth/login`
- **Method**: `POST`
- **Auth required**: No
- **Request Body**:
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Success Response**:
  - **Code**: 200 OK
  - **Content**:
    ```json
    {
      "token": "jwt-token-string",
      "user": {
        "userId": 1,
        "username": "username"
      }
    }
    ```
- **Error Response**:
  - **Code**: 401 UNAUTHORIZED
  - **Content**: `"Invalid username or password"`

#### Register

Creates a new user account.

- **URL**: `/auth/register`
- **Method**: `POST`
- **Auth required**: No
- **Request Body**:
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Success Response**:
  - **Code**: 201 CREATED
  - **Content**:
    ```json
    {
      "userId": 1,
      "username": "username"
    }
    ```
- **Error Response**:
  - **Code**: 400 BAD REQUEST
  - **Content**: `"Username already exists"`

---

### Products

#### Get All Products

Retrieves a list of all products, with optional filtering.

- **URL**: `/products`
- **Method**: `GET`
- **Auth required**: No
- **Query Parameters**:
  - `category` (optional): Filter products by category
  - `search` (optional): Search products by name
- **Success Response**:
  - **Code**: 200 OK
  - **Content**:
    ```json
    [
      {
        "productId": 1,
        "name": "Product Name",
        "category": "Category",
        "price": 9.99,
        "currentStock": 100,
        "description": "Product description",
        "imageUrl": "product-image.jpg",
        "createdAt": "2023-01-01T12:00:00",
        "updatedAt": "2023-01-01T12:00:00"
      }
    ]
    ```

#### Get Product by ID

Retrieves a specific product by its ID.

- **URL**: `/products/{productId}`
- **Method**: `GET`
- **Auth required**: No
- **URL Parameters**:
  - `productId`: ID of the product to retrieve
- **Success Response**:
  - **Code**: 200 OK
  - **Content**:
    ```json
    {
      "productId": 1,
      "name": "Product Name",
      "category": "Category",
      "price": 9.99,
      "currentStock": 100,
      "description": "Product description",
      "imageUrl": "product-image.jpg",
      "createdAt": "2023-01-01T12:00:00",
      "updatedAt": "2023-01-01T12:00:00"
    }
    ```
- **Error Response**:
  - **Code**: 404 NOT FOUND

#### Create Product

Creates a new product (admin only).

- **URL**: `/products`
- **Method**: `POST`
- **Auth required**: Yes (Admin)
- **Request Body**:
  ```json
  {
    "name": "Product Name",
    "category": "Category",
    "price": 9.99,
    "currentStock": 100,
    "description": "Product description",
    "imageUrl": "product-image.jpg"
  }
  ```
- **Success Response**:
  - **Code**: 201 CREATED
  - **Content**: Created product object
- **Error Response**:
  - **Code**: 400 BAD REQUEST
  - **Content**: Validation errors

#### Update Product

Updates an existing product (admin only).

- **URL**: `/products/{productId}`
- **Method**: `PUT`
- **Auth required**: Yes (Admin)
- **URL Parameters**:
  - `productId`: ID of the product to update
- **Request Body**:
  ```json
  {
    "name": "Updated Product Name",
    "category": "Category",
    "price": 19.99,
    "currentStock": 50,
    "description": "Updated product description",
    "imageUrl": "product-image.jpg"
  }
  ```
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: Updated product object
- **Error Response**:
  - **Code**: 404 NOT FOUND

#### Delete Product

Deletes a product (admin only).

- **URL**: `/products/{productId}`
- **Method**: `DELETE`
- **Auth required**: Yes (Admin)
- **URL Parameters**:
  - `productId`: ID of the product to delete
- **Success Response**:
  - **Code**: 204 NO CONTENT
- **Error Response**:
  - **Code**: 404 NOT FOUND

---

### Inventory Management

#### Get Current Inventory

Retrieves the current inventory status (admin only).

- **URL**: `/inventory`
- **Method**: `GET`
- **Auth required**: Yes (Admin)
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: List of products with stock information

#### Get Low Stock Products

Retrieves products with stock levels below a specified threshold (admin only).

- **URL**: `/inventory/low-stock`
- **Method**: `GET`
- **Auth required**: Yes (Admin)
- **Query Parameters**:
  - `threshold` (optional, default: 10): Stock level threshold
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: List of products with low stock

#### Update Product Stock

Updates the stock level of a specific product (admin only).

- **URL**: `/inventory/{productId}`
- **Method**: `PUT`
- **Auth required**: Yes (Admin)
- **URL Parameters**:
  - `productId`: ID of the product to update
- **Request Body**:
  ```json
  {
    "currentStock": 100
  }
  ```
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: Updated product object
- **Error Response**:
  - **Code**: 404 NOT FOUND

---

### Cart Management

#### Get Cart

Retrieves the current user's shopping cart.

- **URL**: `/cart`
- **Method**: `GET`
- **Auth required**: Yes
- **Success Response**:
  - **Code**: 200 OK
  - **Content**:
    ```json
    [
      {
        "itemId": 1,
        "userId": 1,
        "productId": 1,
        "productName": "Product Name",
        "productPrice": 9.99,
        "productImageUrl": "product-image.jpg",
        "quantity": 2,
        "subtotal": 19.98
      }
    ]
    ```

#### Get Cart Total

Retrieves the total price and item count for the current user's cart.

- **URL**: `/cart/total`
- **Method**: `GET`
- **Auth required**: Yes
- **Success Response**:
  - **Code**: 200 OK
  - **Content**:
    ```json
    {
      "total": 19.98,
      "itemCount": 1
    }
    ```

#### Add to Cart

Adds a product to the user's cart.

- **URL**: `/cart`
- **Method**: `POST`
- **Auth required**: Yes
- **Request Body**:
  ```json
  {
    "productId": 1,
    "quantity": 2
  }
  ```
- **Success Response**:
  - **Code**: 201 CREATED
  - **Content**: Created cart item object
- **Error Response**:
  - **Code**: 400 BAD REQUEST
  - **Content**: `"Product not found"`

#### Update Cart Item

Updates the quantity of a cart item.

- **URL**: `/cart/{itemId}`
- **Method**: `PUT`
- **Auth required**: Yes
- **URL Parameters**:
  - `itemId`: ID of the cart item to update
- **Request Body**:
  ```json
  {
    "quantity": 3
  }
  ```
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: Updated cart item object
- **Error Response**:
  - **Code**: 404 NOT FOUND

#### Remove Cart Item

Removes an item from the user's cart.

- **URL**: `/cart/{itemId}`
- **Method**: `DELETE`
- **Auth required**: Yes
- **URL Parameters**:
  - `itemId`: ID of the cart item to remove
- **Success Response**:
  - **Code**: 204 NO CONTENT
- **Error Response**:
  - **Code**: 404 NOT FOUND

---

### Order Management

#### Get Order History

Retrieves the order history for the current user.

- **URL**: `/orders`
- **Method**: `GET`
- **Auth required**: Yes
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: List of user's orders

#### Get Order by ID

Retrieves a specific order by its ID.

- **URL**: `/orders/{orderId}`
- **Method**: `GET`
- **Auth required**: Yes
- **URL Parameters**:
  - `orderId`: ID of the order to retrieve
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: Order object
- **Error Response**:
  - **Code**: 404 NOT FOUND

#### Place Order

Creates a new order from the user's cart.

- **URL**: `/orders`
- **Method**: `POST`
- **Auth required**: Yes
- **Success Response**:
  - **Code**: 201 CREATED
  - **Content**: Created order object
- **Error Response**:
  - **Code**: 400 BAD REQUEST
  - **Content**: `"Cannot create order from empty cart"`

#### Update Order Status

Updates the status of an order (admin only).

- **URL**: `/orders/{orderId}`
- **Method**: `PUT`
- **Auth required**: Yes (Admin)
- **URL Parameters**:
  - `orderId`: ID of the order to update
- **Request Body**:
  ```json
  {
    "status": "processing"
  }
  ```
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: Updated order object
- **Error Response**:
  - **Code**: 404 NOT FOUND

---

### Predictions and Recommendations

#### Get Demand Predictions

Retrieves demand forecasts with optional filtering.

- **URL**: `/predictions`
- **Method**: `GET`
- **Auth required**: Yes (Admin)
- **Query Parameters**:
  - `productId` (optional): Filter by product ID
  - `category` (optional): Filter by product category
  - `dateRange` (optional): Filter by date range
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: List of demand predictions

#### Get Product Recommendations

Retrieves product recommendations for a specific product.

- **URL**: `/recommendations`
- **Method**: `GET`
- **Auth required**: No
- **Query Parameters**:
  - `productId`: ID of the product to get recommendations for
- **Success Response**:
  - **Code**: 200 OK
  - **Content**: List of recommended product IDs
- **Error Response**:
  - **Code**: 400 BAD REQUEST
  - **Content**: `"Product ID is required"`

---

## API Object Models

### Product

```json
{
  "productId": 1,
  "name": "Product Name",
  "category": "Category",
  "price": 9.99,
  "currentStock": 100,
  "description": "Product description",
  "imageUrl": "product-image.jpg",
  "createdAt": "2023-01-01T12:00:00",
  "updatedAt": "2023-01-01T12:00:00"
}
```

### Cart Item

```json
{
  "itemId": 1,
  "userId": 1,
  "productId": 1,
  "productName": "Product Name",
  "productPrice": 9.99,
  "productImageUrl": "product-image.jpg",
  "quantity": 2,
  "subtotal": 19.98
}
```

### Order

```json
{
  "orderId": 1,
  "user": {
    "userId": 1,
    "username": "username"
  },
  "orderDate": "2023-01-01T12:00:00",
  "status": "placed",
  "orderItems": [
    {
      "orderItemId": 1,
      "product": {
        "productId": 1,
        "name": "Product Name"
      },
      "quantity": 2,
      "priceAtOrder": 9.99
    }
  ],
  "totalAmount": 19.98
}
```

### Prediction

```json
{
  "predictionId": 1,
  "productId": 1,
  "predictedQuantity": 50,
  "confidence": 0.85,
  "forecastDate": "2023-01-10",
  "predictionDate": "2023-01-01"
}
```

## Error Handling

The API uses standard HTTP status codes to indicate the result of a request:

- **200 OK**: The request was successful
- **201 Created**: A resource was successfully created
- **204 No Content**: The request was successful, but there is no content to return
- **400 Bad Request**: The request was invalid
- **401 Unauthorized**: Authentication is required or failed
- **403 Forbidden**: The client doesn't have permission to access the resource
- **404 Not Found**: The requested resource was not found
- **500 Internal Server Error**: An unexpected error occurred on the server

Error responses typically include a message describing the error.
