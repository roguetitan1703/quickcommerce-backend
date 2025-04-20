package com.example.quickcommerce.recommendation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Client for the Product Recommendation Service
 */
@Component
public class RecommendationClient {
    private static final Logger logger = LoggerFactory.getLogger(RecommendationClient.class);

    private final RestTemplate restTemplate;
    private final String recommendationApiUrl;

    public RecommendationClient(
            @Value("${recommendation.api.url:http://localhost:5000}") String recommendationApiUrl) {
        this.restTemplate = new RestTemplate();
        this.recommendationApiUrl = recommendationApiUrl;
        logger.info("Recommendation API URL: {}", recommendationApiUrl);
    }

    /**
     * Get product recommendations for a given product ID
     *
     * @param productId The product ID to get recommendations for
     * @param limit     Maximum number of recommendations to return
     * @param threshold Minimum confidence threshold
     * @return List of recommended product IDs with confidence scores
     */
    public List<ProductRecommendation> getRecommendations(Long productId, int limit, double threshold) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(recommendationApiUrl)
                    .path("/recommendations/product/{productId}")
                    .queryParam("limit", limit)
                    .queryParam("threshold", threshold)
                    .buildAndExpand(productId)
                    .toUriString();

            ResponseEntity<RecommendationResponse> response = restTemplate.getForEntity(
                    url, RecommendationResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody().getRecommendations();
            } else {
                logger.warn("Failed to get recommendations for product {}: {}",
                        productId, response.getStatusCode());
                return Collections.emptyList();
            }
        } catch (Exception e) {
            logger.error("Error getting recommendations for product {}: {}", productId, e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Generate recommendation rules by running the Apriori algorithm
     *
     * @param minSupport    Minimum support threshold for frequent itemsets
     * @param minConfidence Minimum confidence threshold for rules
     * @return True if rules were generated successfully, false otherwise
     */
    public boolean generateRecommendations(double minSupport, double minConfidence) {
        try {
            String url = recommendationApiUrl + "/recommendations/generate";

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("min_support", minSupport);
            requestBody.put("min_confidence", minConfidence);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestBody, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.info("Generated recommendation rules: {}", response.getBody());
                return true;
            } else {
                logger.warn("Failed to generate recommendation rules: {}", response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.error("Error generating recommendation rules: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Clear all recommendation rules
     *
     * @return True if rules were cleared successfully, false otherwise
     */
    public boolean clearRecommendations() {
        try {
            String url = recommendationApiUrl + "/recommendations/clear";

            ResponseEntity<Map> response = restTemplate.postForEntity(url, null, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.info("Cleared recommendation rules: {}", response.getBody());
                return true;
            } else {
                logger.warn("Failed to clear recommendation rules: {}", response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.error("Error clearing recommendation rules: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check the health of the recommendation service
     *
     * @return True if the service is healthy, false otherwise
     */
    public boolean isHealthy() {
        try {
            String url = recommendationApiUrl + "/health";

            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            logger.error("Error checking recommendation service health: {}", e.getMessage());
            return false;
        }
    }
}