package com.example.quickcommerce.controller;

import com.example.quickcommerce.model.Prediction;
import com.example.quickcommerce.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    // GET /api/predictions - Get demand predictions with optional filtering
    @GetMapping("/predictions")
    public ResponseEntity<List<Prediction>> getPredictions(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String dateRange) {

        List<Prediction> predictions;

        // Filter predictions based on provided parameters
        if (productId != null) {
            predictions = predictionService.getPredictionsByProductId(productId);
        } else if (category != null && !category.isEmpty()) {
            predictions = predictionService.getPredictionsByCategory(category);
        } else {
            predictions = predictionService.getAllPredictions();
        }

        return ResponseEntity.ok(predictions);
    }

    // GET /api/recommendations - Get product recommendations for a given product
    @GetMapping("/recommendations")
    public ResponseEntity<List<Long>> getRecommendations(
            @RequestParam Long productId) {

        List<Long> recommendedProductIds = predictionService.getRecommendationsForProduct(productId);
        return ResponseEntity.ok(recommendedProductIds);
    }
}
