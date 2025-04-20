package com.example.quickcommerce.dto;

import com.example.quickcommerce.model.Product;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Product entities
 */
public class ProductDTO {
    private Long productId;
    private String name;
    private Long categoryId;
    private Double price;
    private Integer discountPercent;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt;
    private Boolean isActive;

    // Default constructor
    public ProductDTO() {
    }

    // Constructor to convert from Product entity
    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.categoryId = product.getCategoryId();
        this.price = product.getPrice();
        this.discountPercent = product.getDiscountPercent();
        this.description = product.getDescription();
        this.imageUrl = product.getImageUrl();
        this.createdAt = product.getCreatedAt();
        this.isActive = product.getIsActive();
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // Convert back to Product entity
    public Product toEntity() {
        Product product = new Product();
        product.setProductId(this.productId);
        product.setName(this.name);
        product.setCategoryId(this.categoryId);
        product.setPrice(this.price);
        product.setDiscountPercent(this.discountPercent);
        product.setDescription(this.description);
        product.setImageUrl(this.imageUrl);
        product.setCreatedAt(this.createdAt);
        product.setIsActive(this.isActive);
        return product;
    }
}