
package com.example.quickcommerce.controller;

import com.example.quickcommerce.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping("/predictions")
    public ResponseEntity<?> getPredictions(@RequestParam(required = false) String productId) {
        if (productId != null) {
            return ResponseEntity.ok(predictionService.getDemandPredictionForProduct(productId));
        } else {
            return ResponseEntity.ok(predictionService.getAllDemandPredictions());
        }
    }
}
