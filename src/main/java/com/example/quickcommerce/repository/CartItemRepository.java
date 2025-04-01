package com.example.quickcommerce.repository;

import com.example.quickcommerce.model.CartItem;
import com.example.quickcommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Find all cart items for a specific user
    List<CartItem> findByUser(User user);

    // Find a specific cart item for a user and product
    Optional<CartItem> findByUserAndProductProductId(User user, Long productId);
}