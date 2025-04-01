
package com.example.quickcommerce.service;

import com.example.quickcommerce.model.Product;
import com.example.quickcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getCurrentInventory() {
        return productRepository.findAll();
    }

    public Product updateInventory(Product product) {
        // Business logic for inventory update (e.g., stock level checks)
        return productRepository.save(product);
    }
}
