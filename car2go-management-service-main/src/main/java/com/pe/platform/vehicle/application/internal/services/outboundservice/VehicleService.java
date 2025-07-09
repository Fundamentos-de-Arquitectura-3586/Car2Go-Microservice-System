package com.pe.platform.vehicle.application.internal.services.outboundservice;

import com.pe.platform.vehicle.domain.model.dto.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

import com.pe.platform.shared.infrastructure.security.JwtTokenUtil;

@Service
public class VehicleService {
    private final WebClient webClient;
        private static final String PROFILE_SERVICE_NAME = "profile-service";



    public VehicleService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
            .baseUrl("http://" +PROFILE_SERVICE_NAME + "/api/v1/users")
            .build();
    }

    public boolean validateUserExists(String userId) {
        try {
            String jwtToken = JwtTokenUtil.getCurrentJwtTokenWithBearer();
            
            WebClient.RequestHeadersSpec<?> request = webClient.get().uri("/{id}", userId);
            
            // Agregar el JWT token si está disponible
            if (jwtToken != null) {
                request = request.header("Authorization", jwtToken);
            }
            
            request.retrieve()
                .bodyToMono(Boolean.class)
                .block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
