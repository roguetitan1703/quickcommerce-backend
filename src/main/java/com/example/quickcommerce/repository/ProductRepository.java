package com.example.quickcommerce.repository;

import com.example.quickcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Find products by category
    List<Product> findByCategory(String category);

    // Find products where name contains the search term (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);
}
