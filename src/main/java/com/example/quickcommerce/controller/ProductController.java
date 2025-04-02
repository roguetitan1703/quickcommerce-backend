package com.example.quickcommerce.controller;

import com.example.quickcommerce.dto.ProductDTO;
import com.example.quickcommerce.model.Product;
import com.example.quickcommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
// Removing CrossOrigin as it's handled by WebConfig
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET /api/products - Fetch all products with optional filtering
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {
        List<Product> products;
        if (category != null && !category.isEmpty()) {
            products = productService.getProductsByCategory(category);
        } else if (search != null && !search.isEmpty()) {
            products = productService.searchProducts(search);
        } else {
            products = productService.getAllProducts();
        }

        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDTOs);
    }

    // GET /api/products/{id} - Get single product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        Optional<Product> productOpt = productService.getProductById(productId);

        return productOpt
                .map(product -> ResponseEntity.ok(new ProductDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/products - Create new product (Admin only)
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = productDTO.toEntity();
        // Set creation and update timestamps
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(new ProductDTO(savedProduct), HttpStatus.CREATED);
    }

    // PUT /api/products/{id} - Update product (Admin only)
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductDTO productDTO) {

        if (!productService.productExists(productId)) {
            return ResponseEntity.notFound().build();
        }

        Product product = productDTO.toEntity();
        product.setProductId(productId);
        // Update the update timestamp
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(new ProductDTO(updatedProduct));
    }

    // DELETE /api/products/{id} - Delete product (Admin only)
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        if (!productService.productExists(productId)) {
            return ResponseEntity.notFound().build();
        }

        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}