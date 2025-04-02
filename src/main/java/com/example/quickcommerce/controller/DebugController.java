package com.example.quickcommerce.controller;

import com.example.quickcommerce.model.Product;
import com.example.quickcommerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/debug")
public class DebugController {
    private static final Logger logger = LoggerFactory.getLogger(DebugController.class);

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        logger.info("Health check endpoint called");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now().toString());

        try {
            // Count products
            long productCount = productRepository.count();
            response.put("productCount", productCount);
            response.put("message", "Product repository is accessible");
            logger.info("Health check successful. Product count: {}", productCount);
        } catch (Exception e) {
            logger.error("Error during health check", e);
            response.put("error", e.getMessage());
            response.put("message", "Error accessing product repository");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/headers")
    public ResponseEntity<Map<String, String>> showHeaders(HttpServletRequest request) {
        logger.info("Headers endpoint called");
        Map<String, String> headers = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
            logger.debug("Header: {} = {}", headerName, headerValue);
        }

        // Also log request details
        logger.info("Request method: {}", request.getMethod());
        logger.info("Request URI: {}", request.getRequestURI());
        logger.info("Remote address: {}", request.getRemoteAddr());

        return ResponseEntity.ok(headers);
    }

    @GetMapping("/add-test-product")
    public ResponseEntity<Map<String, Object>> addTestProduct() {
        logger.info("Add test product endpoint called");
        Map<String, Object> response = new HashMap<>();

        try {
            Product product = new Product();
            product.setName("Test Product");
            product.setDescription("Test product added for debugging");
            product.setCategory("Test");
            product.setPrice(999.99);
            product.setCurrentStock(10);
            product.setImageUrl("https://placehold.co/600x400?text=Test+Product");
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdatedAt(LocalDateTime.now());

            Product savedProduct = productRepository.save(product);
            response.put("success", true);
            response.put("message", "Test product added successfully");
            response.put("productId", savedProduct.getProductId());
            logger.info("Test product added with ID: {}", savedProduct.getProductId());
        } catch (Exception e) {
            logger.error("Error adding test product", e);
            response.put("success", false);
            response.put("error", e.getMessage());
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-products-raw")
    public ResponseEntity<List<Product>> getAllProductsRaw() {
        logger.info("All products raw endpoint called");
        List<Product> products = productRepository.findAll();
        logger.info("Retrieved {} products", products.size());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getCategories() {
        logger.info("Categories endpoint called");
        Map<String, Object> response = new HashMap<>();

        try {
            // Get all unique categories
            List<String> categories = productRepository.findAll().stream()
                    .map(Product::getCategory)
                    .distinct()
                    .collect(Collectors.toList());

            response.put("success", true);
            response.put("categories", categories);
            response.put("count", categories.size());
            logger.info("Retrieved {} categories", categories.size());
        } catch (Exception e) {
            logger.error("Error retrieving categories", e);
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test-cors")
    public ResponseEntity<Map<String, Object>> testCors(HttpServletRequest request) {
        logger.info("CORS test endpoint called from: {}", request.getRemoteAddr());
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "CORS is working");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("origin", request.getHeader("Origin"));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/request-dump")
    public ResponseEntity<Map<String, Object>> requestDump(HttpServletRequest request) {
        logger.info("Request dump endpoint called from: {}", request.getRemoteAddr());
        Map<String, Object> response = new HashMap<>();

        // Client information
        Map<String, Object> clientInfo = new HashMap<>();
        clientInfo.put("remoteAddr", request.getRemoteAddr());
        clientInfo.put("remoteHost", request.getRemoteHost());
        clientInfo.put("remotePort", request.getRemotePort());
        clientInfo.put("localAddr", request.getLocalAddr());
        clientInfo.put("localPort", request.getLocalPort());
        clientInfo.put("serverName", request.getServerName());
        clientInfo.put("protocol", request.getProtocol());
        clientInfo.put("scheme", request.getScheme());
        clientInfo.put("method", request.getMethod());
        clientInfo.put("requestURI", request.getRequestURI());
        clientInfo.put("requestURL", request.getRequestURL().toString());

        // Headers
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headers.put(headerName, headerValue);
            logger.debug("Header: {} = {}", headerName, headerValue);
        }

        // Server configuration
        Map<String, Object> serverConfig = new HashMap<>();
        serverConfig.put("corsConfig",
                "allowedOrigins=[http://localhost:3000, http://localhost:3001, http://192.168.109.120:3000, http://192.168.109.120:3001], allowCredentials=true");

        // Add everything to response
        response.put("clientInfo", clientInfo);
        response.put("headers", headers);
        response.put("serverConfig", serverConfig);
        response.put("timestamp", LocalDateTime.now().toString());

        logger.info("Returning complete request dump");
        return ResponseEntity.ok(response);
    }
}