package com.example.quickcommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration for serving static documentation resources and redirecting
 * documentation requests.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Register a resource handler for the /docs/** path
        registry.addResourceHandler("/docs/**")
                .addResourceLocations("classpath:/static/docs/")
                .setCachePeriod(3600); // Cache for 1 hour (in seconds)
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirect /docs to /docs/index.html
        registry.addRedirectViewController("/docs", "/docs/index.html");
    }
}