package com.example.quickcommerce.controller;

import com.example.quickcommerce.dto.CartItemDTO;
import com.example.quickcommerce.model.CartItem;
import com.example.quickcommerce.model.User;
import com.example.quickcommerce.service.CartService;
import com.example.quickcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    // Temporary method to get current user (to be replaced with proper
    // authentication)
    private User getCurrentUser() {
        // For now, just get the first user
        return userService.getUserById(1L).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // GET /api/cart - Fetch current user cart
    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getCart() {
        User currentUser = getCurrentUser();
        List<CartItem> cartItems = cartService.getCartItems(currentUser);

        List<CartItemDTO> cartItemDTOs = cartItems.stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(cartItemDTOs);
    }

    // POST /api/cart - Add item to cart
    @PostMapping
    public ResponseEntity<CartItemDTO> addToCart(@RequestBody Map<String, Object> payload) {
        Long productId = Long.valueOf(payload.get("productId").toString());
        Integer quantity = Integer.valueOf(payload.get("quantity").toString());

        User currentUser = getCurrentUser();
        CartItem addedItem = cartService.addToCart(currentUser, productId, quantity);

        return new ResponseEntity<>(new CartItemDTO(addedItem), HttpStatus.CREATED);
    }

    // PUT /api/cart/{itemId} - Update cart item quantity
    @PutMapping("/{itemId}")
    public ResponseEntity<?> updateCartItem(
            @PathVariable Long itemId,
            @RequestBody Map<String, Object> payload) {

        Integer quantity = Integer.valueOf(payload.get("quantity").toString());

        try {
            CartItem updatedItem = cartService.updateCartItemQuantity(itemId, quantity);
            return ResponseEntity.ok(new CartItemDTO(updatedItem));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/cart/{itemId} - Remove item from cart
    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long itemId) {
        cartService.removeFromCart(itemId);
        return ResponseEntity.noContent().build();
    }

    // GET /api/cart/total - Get cart total
    @GetMapping("/total")
    public ResponseEntity<Map<String, Object>> getCartTotal() {
        User currentUser = getCurrentUser();
        List<CartItem> cartItems = cartService.getCartItems(currentUser);

        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        return ResponseEntity.ok(Map.of(
                "total", total,
                "itemCount", cartItems.size()));
    }
}