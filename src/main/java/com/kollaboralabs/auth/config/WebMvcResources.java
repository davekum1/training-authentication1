package com.kollaboralabs.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Disable path extension to make Spring ignore as content type if it couldn't find converter
 * One of the goal for this class is to disable . (dot) in the request parameters
 * Note: This will apply to all controllers and route in the auth service
 */
@Configuration
public class WebMvcResources extends WebMvcConfigurerAdapter {
    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        // Turn off suffix-based content negotiation
        configurer.favorPathExtension(false);
    }
}