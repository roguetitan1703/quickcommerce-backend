# Snap Quick Commerce API Documentation

This directory contains all the documentation resources for the Snap Quick Commerce API.

## Documentation Structure

- **index.html** - The main documentation page with all API details
- **styles.css** - CSS styling for the documentation
- **scripts.js** - JavaScript functionality for the documentation
- **SnapQuickCommerce.postman_collection.json** - Postman collection for testing the API

## Local Development

To view the documentation locally:

1. Run the Spring Boot application
2. Access the documentation at http://localhost:8080/docs

## Updating the Documentation

When adding new API endpoints or modifying existing ones, follow these steps to update the documentation:

1. Update the API description in `index.html` under the appropriate section
2. Add any new endpoints with detailed request/response information
3. Update the Postman collection for testing
4. Ensure that the Swagger UI configuration in `SwaggerConfig.java` is also updated

## Documentation Best Practices

- Keep endpoint descriptions clear and concise
- Include all request parameters, headers, and body schema
- Document all possible response statuses and formats
- Provide example requests and responses
- Use consistent formatting across all endpoint documentation

## Integration with Swagger UI

The API documentation integrates with Swagger UI. Developers can also access the interactive Swagger documentation at:

```
http://localhost:8080/swagger-ui.html
```

## Contact

For any documentation-related questions or suggestions, please contact the development team.
