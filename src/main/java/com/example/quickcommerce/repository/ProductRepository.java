package com.example.quickcommerce.repository;

import com.example.quickcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Find products by category ID
    List<Product> findByCategoryId(Long categoryId);

    // Find products where name contains the search term (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.description LIKE %?1%")
    List<Product> findByNameOrDescriptionContaining(String keyword);

    List<Product> findTop10ByOrderByCreatedAtDesc();

    // Find products by category name
    List<Product> findByCategory(String category);
}
