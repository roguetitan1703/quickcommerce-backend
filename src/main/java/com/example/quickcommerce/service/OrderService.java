package com.example.quickcommerce.service;

import com.example.quickcommerce.model.*;
import com.example.quickcommerce.repository.OrderRepository;
import com.example.quickcommerce.repository.ProductRepository;
import com.example.quickcommerce.repository.UserRepository;
import com.example.quickcommerce.dto.OrderItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private InventoryService inventoryService;

    // Get all orders (admin functionality)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get orders for a specific user
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    // Get order by ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Create order from user's cart
    @Transactional
    public Order createOrderFromCart(User user) {
        // Get user's cart items
        List<CartItem> cartItems = cartService.getCartItems(user);

        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cannot create order from empty cart");
        }

        // Create new order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("placed");

        // Calculate total amount
        double totalAmount = 0.0;

        // Add order items from cart
        for (CartItem cartItem : cartItems) {
            // Check stock availability
            if (!inventoryService.hasEnoughStock(cartItem.getProduct().getProductId(), cartItem.getQuantity())) {
                throw new RuntimeException("Insufficient stock for product: " + cartItem.getProduct().getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtOrder(cartItem.getProduct().getPrice());

            // Add to order
            order.addOrderItem(orderItem);

            // Add to total
            totalAmount += orderItem.getPriceAtOrder() * orderItem.getQuantity();

            // Update inventory (decrease stock)
            inventoryService.updateStock(cartItem.getProduct().getProductId(), cartItem.getQuantity());
        }

        order.setTotalAmount(totalAmount);

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Clear the cart
        cartService.clearCart(user);

        return savedOrder;
    }

    // Update order status
    public Order updateOrderStatus(Long orderId, String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);

        if (orderOpt.isEmpty()) {
            throw new IllegalArgumentException("Order not found");
        }

        Order order = orderOpt.get();
        order.setStatus(status);

        return orderRepository.save(order);
    }

    @Transactional
    public Order createOrder(Long userId, List<OrderItemDTO> items) {
        // Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        // Check stock availability
        for (OrderItemDTO item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));

            // Check stock through InventoryService
            if (!inventoryService.hasEnoughStock(product.getProductId(), item.getQuantity())) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
        }

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        // Calculate total amount
        double totalAmount = 0.0;

        // Create order items and update stock
        for (OrderItemDTO item : items) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtOrder(product.getPrice());

            // Add to order
            order.addOrderItem(orderItem);

            // Add to total
            totalAmount += orderItem.getPriceAtOrder() * orderItem.getQuantity();

            // Update stock through InventoryService
            inventoryService.updateStock(product.getProductId(), item.getQuantity());
        }

        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }
}
