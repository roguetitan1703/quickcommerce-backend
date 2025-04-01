package com.example.quickcommerce.repository;

import com.example.quickcommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Add any custom query methods if needed
}