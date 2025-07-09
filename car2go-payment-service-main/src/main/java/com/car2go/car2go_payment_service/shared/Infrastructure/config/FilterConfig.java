package com.car2go.car2go_payment_service.shared.Infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.car2go.car2go_payment_service.shared.Infrastructure.security.JwtAuthenticationFilter;

@Configuration
public class FilterConfig {


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
        
    }
}
