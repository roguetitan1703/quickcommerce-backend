package com.example.quickcommerce.dto;

import com.example.quickcommerce.model.CartItem;

/**
 * Data Transfer Object for CartItem entities
 */
public class CartItemDTO {
    private Long itemId;
    private Long userId;
    private Long productId;
    private String productName;
    private Double productPrice;
    private String productImageUrl;
    private Integer quantity;

    // Default constructor
    public CartItemDTO() {
    }

    // Constructor to convert from CartItem entity
    public CartItemDTO(CartItem cartItem) {
        this.itemId = cartItem.getItemId();
        this.userId = cartItem.getUser().getUserId();
        this.productId = cartItem.getProduct().getProductId();
        this.productName = cartItem.getProduct().getName();
        this.productPrice = cartItem.getProduct().getPrice();
        this.productImageUrl = cartItem.getProduct().getImageUrl();
        this.quantity = cartItem.getQuantity();
    }

    // Getters and Setters
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Calculate subtotal for this cart item
    public Double getSubtotal() {
        return this.productPrice * this.quantity;
    }
}