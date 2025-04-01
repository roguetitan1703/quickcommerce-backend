
package com.example.quickcommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.quickcommerce.repository") // Enable JPA repositories
@EntityScan("com.example.quickcommerce.model") // Enable entity scanning
public class QuickCommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickCommerceApplication.class, args);
    }
}
