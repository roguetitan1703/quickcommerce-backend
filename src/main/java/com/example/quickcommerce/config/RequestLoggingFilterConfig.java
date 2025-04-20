package com.example.quickcommerce.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.UUID;

@Configuration
public class RequestLoggingFilterConfig {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilterConfig.class);

    // Basic request logging
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setAfterMessagePrefix("REQUEST DATA: ");
        return filter;
    }

    // Enhanced logging with request/response details and timing
    @Bean
    public OncePerRequestFilter enhancedLoggingFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                    FilterChain filterChain)
                    throws ServletException, IOException {

                // Generate a unique request ID
                String requestId = UUID.randomUUID().toString().substring(0, 8);

                // Wrap the request and response for reading
                ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
                ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

                // Record the start time
                long startTime = System.currentTimeMillis();

                try {
                    // Log the incoming request using string concatenation
                    String requestLog = "[REQ:" + requestId + "] " +
                            request.getMethod() + " " +
                            request.getRequestURI() +
                            " (Client: " + request.getRemoteAddr() + ")";
                    logger.info(requestLog);

                    // Process the request
                    filterChain.doFilter(requestWrapper, responseWrapper);
                } finally {
                    // Calculate request duration
                    long duration = System.currentTimeMillis() - startTime;

                    // Log the response details using string concatenation
                    String responseLog = "[RESP:" + requestId + "] " +
                            request.getMethod() + " " +
                            request.getRequestURI() +
                            " - Status: " + responseWrapper.getStatus() +
                            " (" + duration + "ms)";
                    logger.info(responseLog);

                    // Copy the cached response back to the response
                    responseWrapper.copyBodyToResponse();
                }
            }
        };
    }
}