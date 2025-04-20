package com.example.quickcommerce.controller;

import com.example.quickcommerce.model.Product;
import com.example.quickcommerce.service.RecommendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for recommendation endpoints
 */
@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {
        private static final Logger logger = LoggerFactory.getLogger(RecommendationController.class);

        private final RecommendationService recommendationService;

        @Autowired
        public RecommendationController(RecommendationService recommendationService) {
                this.recommendationService = recommendationService;
        }

        /**
         * Get product recommendations
         *
         * @param productId The product ID to get recommendations for
         * @param limit     Maximum number of recommendations to return (optional)
         * @return List of recommended products
         */
        @GetMapping("/product/{productId}")
        public ResponseEntity<List<Product>> getProductRecommendations(
                        @PathVariable Long productId,
                        @RequestParam(defaultValue = "5") int limit) {

                logger.debug("Getting product recommendations for product ID: {}, limit: {}", productId, limit);
                List<Product> recommendations = recommendationService.getProductRecommendations(productId, limit);

                return ResponseEntity.ok(recommendations);
        }

        /**
         * Get user recommendations
         *
         * @param userId The user ID to get recommendations for
         * @param limit  Maximum number of recommendations to return (optional)
         * @return List of recommended products
         */
        @GetMapping("/user/{userId}")
        public ResponseEntity<List<Product>> getUserRecommendations(
                        @PathVariable Long userId,
                        @RequestParam(defaultValue = "10") int limit) {

                logger.debug("Getting user recommendations for user ID: {}, limit: {}", userId, limit);
                List<Product> recommendations = recommendationService.getUserRecommendations(userId, limit);

                return ResponseEntity.ok(recommendations);
        }

        /**
         * Trigger the generation of recommendation rules
         *
         * @return Status of the operation
         */
        @PostMapping("/generate")
        public ResponseEntity<Map<String, Object>> generateRecommendations() {
                logger.info("Manually triggering recommendation generation");

                boolean success = recommendationService.generateRecommendations();
                Map<String, Object> response = new HashMap<>();

                if (success) {
                        response.put("status", "success");
                        response.put("message", "Recommendation rules generated successfully");
                        return ResponseEntity.ok(response);
                } else {
                        response.put("status", "error");
                        response.put("message", "Failed to generate recommendation rules");
                        return ResponseEntity.badRequest().body(response);
                }
        }

        /**
         * Clear all recommendation rules
         *
         * @return Status of the operation
         */
        @PostMapping("/clear")
        public ResponseEntity<Map<String, Object>> clearRecommendations() {
                logger.info("Manually clearing recommendation rules");

                boolean success = recommendationService.clearRecommendations();
                Map<String, Object> response = new HashMap<>();

                if (success) {
                        response.put("status", "success");
                        response.put("message", "Recommendation rules cleared successfully");
                        return ResponseEntity.ok(response);
                } else {
                        response.put("status", "error");
                        response.put("message", "Failed to clear recommendation rules");
                        return ResponseEntity.badRequest().body(response);
                }
        }
}