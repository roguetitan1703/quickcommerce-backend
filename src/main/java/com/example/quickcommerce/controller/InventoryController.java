package com.example.quickcommerce.controller;

import com.example.quickcommerce.model.Product;
import com.example.quickcommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // GET /api/inventory - Get current inventory status (admin only)
    @GetMapping
    public ResponseEntity<List<Product>> getInventory() {
        return ResponseEntity.ok(inventoryService.getCurrentInventory());
    }

    // GET /api/inventory/low-stock - Get products with low stock (admin only)
    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts(
            @RequestParam(defaultValue = "10") Integer threshold) {
        return ResponseEntity.ok(inventoryService.getLowStockProducts(threshold));
    }

    // PUT /api/inventory/{productId} - Update inventory level manually (admin only)
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateInventory(
            @PathVariable Long productId,
            @RequestBody Map<String, Object> payload) {

        Integer newStock = Integer.valueOf(payload.get("currentStock").toString());

        try {
            Product product = inventoryService.updateProductStock(productId, newStock);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
