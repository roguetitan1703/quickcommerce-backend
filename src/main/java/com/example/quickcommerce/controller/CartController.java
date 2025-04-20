package com.example.quickcommerce.controller;

import com.example.quickcommerce.dto.CartItemDTO;
import com.example.quickcommerce.model.CartItem;
import com.example.quickcommerce.model.User;
import com.example.quickcommerce.service.CartService;
import com.example.quickcommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    // Improved method to get current user with error handling
    private User getCurrentUser() {
        try {
            // For development, find or create a default user with ID 1
            return userService.getUserById(1L)
                    .orElseGet(() -> {
                        logger.warn("Default user not found, attempting to create one");
                        User newUser = new User();
                        newUser.setUsername("default_user");
                        newUser.setPassword("password");
                        return userService.saveUser(newUser);
                    });
        } catch (Exception e) {
            logger.error("Error getting current user: {}", e.getMessage(), e);
            throw new RuntimeException("Error accessing user data");
        }
    }

    // GET /api/cart - Fetch current user cart
    @GetMapping
    public ResponseEntity<?> getCart() {
        try {
            User currentUser = getCurrentUser();
            List<CartItem> cartItems = cartService.getCartItems(currentUser);

            List<CartItemDTO> cartItemDTOs = cartItems.stream()
                    .map(CartItemDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(cartItemDTOs);
        } catch (Exception e) {
            logger.error("Error fetching cart: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to retrieve cart data");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // POST /api/cart - Add item to cart
    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> payload) {
        try {
            if (payload.get("productId") == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Product ID is required"));
            }

            Long productId = Long.valueOf(payload.get("productId").toString());
            Integer quantity = payload.get("quantity") != null ? Integer.valueOf(payload.get("quantity").toString())
                    : 1;

            User currentUser = getCurrentUser();
            CartItem addedItem = cartService.addToCart(currentUser, productId, quantity);

            return new ResponseEntity<>(new CartItemDTO(addedItem), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding to cart: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to add item to cart");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // POST /api/cart/items - Add item to cart (alternative endpoint for frontend
    // compatibility)
    @PostMapping("/items")
    public ResponseEntity<?> addToCartItems(@RequestBody Map<String, Object> payload) {
        return addToCart(payload); // Reuse the same logic
    }

    // PUT /api/cart/{itemId} - Update cart item quantity
    @PutMapping("/{itemId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable Long itemId,
            @RequestBody Map<String, Object> payload) {

        try {
            if (payload.get("quantity") == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Quantity is required"));
            }

            Integer quantity = Integer.valueOf(payload.get("quantity").toString());

            CartItem updatedItem = cartService.updateCartItemQuantity(itemId, quantity);
            return ResponseEntity.ok(new CartItemDTO(updatedItem));
        } catch (IllegalArgumentException e) {
            logger.warn("Cart item not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Cart item not found", "message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error updating cart item: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to update cart item");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // DELETE /api/cart/{itemId} - Remove item from cart
    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long itemId) {
        try {
            cartService.removeFromCart(itemId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error removing cart item: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to remove item from cart");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // GET /api/cart/total - Get cart total
    @GetMapping("/total")
    public ResponseEntity<?> getCartTotal() {
        try {
            User currentUser = getCurrentUser();
            List<CartItem> cartItems = cartService.getCartItems(currentUser);

            double total = cartItems.stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();

            Map<String, Object> response = new HashMap<>();
            response.put("totalAmount", total);
            response.put("itemCount", cartItems.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error calculating cart total: {}", e.getMessage(), e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to calculate cart total");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}