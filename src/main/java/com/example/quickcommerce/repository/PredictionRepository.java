
package com.example.quickcommerce.repository;

import com.example.quickcommerce.model.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
}
