
package com.example.quickcommerce.service;

import com.example.quickcommerce.model.Order;
import com.example.quickcommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order placeNewOrder(Order order) {
        // Business logic for order placement (e.g., inventory reduction, order validation)
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
