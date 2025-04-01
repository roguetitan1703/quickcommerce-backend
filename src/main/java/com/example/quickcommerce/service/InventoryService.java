package com.example.quickcommerce.service;

import com.example.quickcommerce.model.Product;
import com.example.quickcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Get current inventory (all products with stock information)
     */
    public List<Product> getCurrentInventory() {
        return productRepository.findAll();
    }

    /**
     * Get products with stock levels below the specified threshold
     */
    public List<Product> getLowStockProducts(Integer threshold) {
        return productRepository.findAll().stream()
                .filter(product -> product.getCurrentStock() <= threshold)
                .collect(Collectors.toList());
    }

    /**
     * Update product stock level
     */
    public Product updateProductStock(Long productId, Integer newStock) {
        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        Product product = productOpt.get();
        product.setCurrentStock(newStock);

        return productRepository.save(product);
    }
}
