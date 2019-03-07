package com.kollaboralabs.auth.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Value("${spring.application.appSecret}")
    private String appSecret;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages");
        messageBundle.setDefaultEncoding("UTF-8");

        return messageBundle;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
            .addInterceptor(new ApiKeyInterceptor(this.appSecret))
            .addPathPatterns("/api/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver requestParamResolver = new PageableHandlerMethodArgumentResolver();
        requestParamResolver.setFallbackPageable(new PageRequest(0, 100, Sort.Direction.ASC, "uuid"));
        requestParamResolver.setMaxPageSize(1000);
        argumentResolvers.add(requestParamResolver);
        super.addArgumentResolvers(argumentResolvers);
    }
}