package com.example.quickcommerce.controller;

import com.example.quickcommerce.model.Order;
import com.example.quickcommerce.model.User;
import com.example.quickcommerce.service.OrderService;
import com.example.quickcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // Temporary method to get current user (to be replaced with proper
    // authentication)
    private User getCurrentUser() {
        // For now, just get the first user
        return userService.getUserById(1L).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // GET /api/orders - Retrieve order history
    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        User currentUser = getCurrentUser();
        List<Order> orders = orderService.getOrdersByUser(currentUser);
        return ResponseEntity.ok(orders);
    }

    // GET /api/orders/{orderId} - Get a specific order
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        Optional<Order> order = orderService.getOrderById(orderId);

        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/orders - Place an order
    @PostMapping
    public ResponseEntity<?> placeOrder() {
        try {
            User currentUser = getCurrentUser();
            Order createdOrder = orderService.createOrderFromCart(currentUser);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/orders/{orderId} - Update order status (admin only)
    @PutMapping("/{orderId}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody Map<String, String> payload) {

        String status = payload.get("status");

        if (status == null || status.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Status is required");
        }

        try {
            Order updatedOrder = orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(updatedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
