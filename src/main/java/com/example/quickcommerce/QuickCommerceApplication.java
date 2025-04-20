package com.example.quickcommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories("com.example.quickcommerce.repository") // Enable JPA repositories
@EntityScan("com.example.quickcommerce.model") // Enable entity scanning
@EnableScheduling
public class QuickCommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickCommerceApplication.class, args);
    }
}
