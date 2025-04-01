package com.example.quickcommerce.repository;

import com.example.quickcommerce.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    // Find predictions for a specific product
    List<Prediction> findByProductId(Long productId);

    // Find predictions for a list of products
    List<Prediction> findByProductIdIn(List<Long> productIds);
}
