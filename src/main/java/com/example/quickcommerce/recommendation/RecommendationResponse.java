package com.example.quickcommerce.recommendation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Response from the recommendation API
 */
public class RecommendationResponse {

    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("model")
    private String model;

    @JsonProperty("recommendations")
    private List<ProductRecommendation> recommendations;

    // Default constructor
    public RecommendationResponse() {
    }

    // Constructor
    public RecommendationResponse(Long productId, List<ProductRecommendation> recommendations) {
        this.productId = productId;
        this.recommendations = recommendations;
    }

    // Constructor with model
    public RecommendationResponse(Long productId, String model, List<ProductRecommendation> recommendations) {
        this.productId = productId;
        this.model = model;
        this.recommendations = recommendations;
    }

    // Getters and setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ProductRecommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<ProductRecommendation> recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public String toString() {
        return "RecommendationResponse{" +
                "productId=" + productId +
                ", model='" + model + '\'' +
                ", recommendations=" + recommendations +
                '}';
    }
}