package com.example.quickcommerce.repository;

import com.example.quickcommerce.model.Order;
import com.example.quickcommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find orders by user
    List<Order> findByUser(User user);

    // Find orders by status
    List<Order> findByStatus(String status);

    // Find orders by user and status
    List<Order> findByUserAndStatus(User user, String status);
}
