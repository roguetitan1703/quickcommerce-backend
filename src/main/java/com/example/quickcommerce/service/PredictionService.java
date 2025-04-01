package com.example.quickcommerce.service;

import com.example.quickcommerce.model.Prediction;
import com.example.quickcommerce.model.Product;
import com.example.quickcommerce.repository.PredictionRepository;
import com.example.quickcommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Get all demand predictions
     */
    public List<Prediction> getAllPredictions() {
        return predictionRepository.findAll();
    }

    /**
     * Get predictions for a specific product
     */
    public List<Prediction> getPredictionsByProductId(Long productId) {
        // In a real app, this would query the prediction model service
        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isEmpty()) {
            return Collections.emptyList();
        }

        // For now, return any predictions we have in database for this product
        return predictionRepository.findByProductId(productId);
    }

    /**
     * Get predictions for a specific category
     */
    public List<Prediction> getPredictionsByCategory(String category) {
        // Get all products in the category
        List<Product> productsInCategory = productRepository.findByCategory(category);
        List<Long> productIds = productsInCategory.stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());

        // In a real app, this would query the prediction model service
        // For now, return any predictions we have in database for these products
        return predictionRepository.findByProductIdIn(productIds);
    }

    /**
     * Get recommendations for a specific product
     */
    public List<Long> getRecommendationsForProduct(Long productId) {
        // In a real app, this would call the recommendation model service
        // For now, return a simple mock implementation

        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            return Collections.emptyList();
        }

        Product product = productOpt.get();

        // Mock implementation: recommend other products in the same category
        List<Product> similarProducts = productRepository.findByCategory(product.getCategory()).stream()
                .filter(p -> !p.getProductId().equals(productId)) // Exclude the current product
                .limit(5) // Limit to 5 recommendations
                .collect(Collectors.toList());

        return similarProducts.stream()
                .map(Product::getProductId)
                .collect(Collectors.toList());
    }
}
