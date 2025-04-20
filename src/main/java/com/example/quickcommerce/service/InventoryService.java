package com.example.quickcommerce.service;

import com.example.quickcommerce.model.Product;
import com.example.quickcommerce.model.Inventory;
import com.example.quickcommerce.repository.ProductRepository;
import com.example.quickcommerce.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Get current inventory (all products with stock information)
     */
    public List<Inventory> getCurrentInventory() {
        return inventoryRepository.findAll();
    }

    /**
     * Get products with stock levels below the specified threshold
     */
    public List<Inventory> getLowStockProducts(Integer threshold) {
        return inventoryRepository.findAll().stream()
                .filter(inventory -> inventory.getCurrentStock() <= threshold)
                .collect(Collectors.toList());
    }

    /**
     * Update product stock level
     */
    @Transactional
    public Inventory updateProductStock(Long productId, Integer newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseGet(() -> {
                    Inventory newInventory = new Inventory();
                    newInventory.setProduct(product);
                    newInventory.setCurrentStock(0);
                    newInventory.setMinStock(0);
                    newInventory.setMaxStock(1000); // Default max stock
                    return newInventory;
                });

        inventory.setCurrentStock(newStock);
        return inventoryRepository.save(inventory);
    }

    /**
     * Check if there is enough stock for a product
     */
    public boolean hasEnoughStock(Long productId, Integer quantity) {
        return inventoryRepository.findByProductId(productId)
                .map(inventory -> inventory.getCurrentStock() >= quantity)
                .orElse(false);
    }

    /**
     * Update stock by reducing it by the specified quantity
     */
    @Transactional
    public void updateStock(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found in inventory"));

        if (inventory.getCurrentStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        inventory.setCurrentStock(inventory.getCurrentStock() - quantity);
        inventoryRepository.save(inventory);
    }
}
