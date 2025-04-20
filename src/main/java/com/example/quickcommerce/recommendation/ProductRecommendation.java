package com.example.quickcommerce.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a product recommendation with confidence metrics
 */
public class ProductRecommendation {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("confidence")
    private double confidence;

    @JsonProperty("support")
    private double support;

    @JsonProperty("lift")
    private double lift;

    // Default constructor
    public ProductRecommendation() {
    }

    // Constructor with essential fields
    public ProductRecommendation(Long productId, double confidence) {
        this.productId = productId;
        this.confidence = confidence;
    }

    // Full constructor
    public ProductRecommendation(Long productId, double confidence, double support, double lift) {
        this.productId = productId;
        this.confidence = confidence;
        this.support = support;
        this.lift = lift;
    }

    // Getters and setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public double getSupport() {
        return support;
    }

    public void setSupport(double support) {
        this.support = support;
    }

    public double getLift() {
        return lift;
    }

    public void setLift(double lift) {
        this.lift = lift;
    }

    @Override
    public String toString() {
        return "ProductRecommendation{" +
                "productId=" + productId +
                ", confidence=" + confidence +
                ", support=" + support +
                ", lift=" + lift +
                '}';
    }
}