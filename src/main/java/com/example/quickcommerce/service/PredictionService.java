
package com.example.quickcommerce.service;

import com.example.quickcommerce.model.Prediction;
import com.example.quickcommerce.repository.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    public List<Prediction> getAllDemandPredictions() {
        return predictionRepository.findAll();
    }

    public Prediction getDemandPredictionForProduct(String productId) {
        // Logic to fetch prediction for a specific product
        // For now, returning a placeholder
        return new Prediction(); // In real app, fetch from prediction model/DB
    }
}
