
package com.example.quickcommerce.controller;

import com.example.quickcommerce.model.Order;
import com.example.quickcommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        Order placedOrder = orderService.placeNewOrder(order);
        return ResponseEntity.ok(placedOrder);
    }
}
