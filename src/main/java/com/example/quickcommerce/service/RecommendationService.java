package com.example.quickcommerce.service;

import com.example.quickcommerce.recommendation.ProductRecommendation;
import com.example.quickcommerce.recommendation.RecommendationClient;
import com.example.quickcommerce.model.Product;
import com.example.quickcommerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for product recommendations
 */
@Service
public class RecommendationService {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationService.class);

    private final RecommendationClient recommendationClient;
    private final ProductRepository productRepository;

    @Autowired
    public RecommendationService(RecommendationClient recommendationClient, ProductRepository productRepository) {
        this.recommendationClient = recommendationClient;
        this.productRepository = productRepository;

        // Check if recommendation service is available
        boolean isHealthy = recommendationClient.isHealthy();
        logger.info("Recommendation service health check: {}", isHealthy ? "HEALTHY" : "UNAVAILABLE");
    }

    /**
     * Get product recommendations for a specific product
     *
     * @param productId The product ID to get recommendations for
     * @param limit     Maximum number of recommendations to return
     * @return List of recommended products
     */
    public List<Product> getProductRecommendations(Long productId, int limit) {
        if (productId == null) {
            return new ArrayList<>();
        }

        try {
            // Get recommendation data from the recommendation service
            List<ProductRecommendation> recommendations = recommendationClient.getRecommendations(
                    productId, limit, 0.1);

            if (recommendations.isEmpty()) {
                logger.debug("No recommendations found for product: {}", productId);
                return new ArrayList<>();
            }

            // Extract product IDs from recommendations
            List<Long> recommendedProductIds = recommendations.stream()
                    .map(ProductRecommendation::getProductId)
                    .collect(Collectors.toList());

            // Fetch product details from repository
            List<Product> recommendedProducts = productRepository.findAllById(recommendedProductIds);

            // Sort products based on the order of recommendations
            Map<Long, Product> productMap = recommendedProducts.stream()
                    .collect(Collectors.toMap(Product::getProductId, product -> product));

            return recommendations.stream()
                    .map(rec -> productMap.get(rec.getProductId()))
                    .filter(product -> product != null)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Error fetching product recommendations: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Get personalized recommendations for a user
     *
     * @param userId The user ID to get recommendations for
     * @param limit  Maximum number of recommendations to return
     * @return List of recommended products
     */
    public List<Product> getUserRecommendations(Long userId, int limit) {
        if (userId == null) {
            return new ArrayList<>();
        }

        try {
            // TODO: Call user recommendations endpoint when implemented
            // For now, use product recommendations as a fallback

            // Return popular products as a fallback
            return productRepository.findTop10ByOrderByCreatedAtDesc();

        } catch (Exception e) {
            logger.error("Error fetching user recommendations: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Trigger the generation of recommendation rules
     *
     * @return true if successful, false otherwise
     */
    public boolean generateRecommendations() {
        try {
            return recommendationClient.generateRecommendations(0.01, 0.3);
        } catch (Exception e) {
            logger.error("Error generating recommendations: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Clear all recommendation rules
     *
     * @return true if successful, false otherwise
     */
    public boolean clearRecommendations() {
        try {
            return recommendationClient.clearRecommendations();
        } catch (Exception e) {
            logger.error("Error clearing recommendations: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Scheduled task to regenerate recommendations periodically
     * Runs once a day at 3 AM
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void scheduledRecommendationUpdate() {
        logger.info("Running scheduled recommendation update");
        generateRecommendations();
    }
}