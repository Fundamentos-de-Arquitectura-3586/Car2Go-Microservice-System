package com.pe.platform.vehicle.application.internal.services.outboundservice;

import com.pe.platform.vehicle.domain.model.dto.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Optional;

@Service
public class VehicleService {
    private final WebClient webClient;

    public VehicleService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
            .baseUrl("http://localhost:8080")
            .build();
    }

    public boolean validateUserExists(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();

        try {
            return webClient.get()
                    .uri("/iam-service/api/v1/users/{id}", userId)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (Exception e) {
            return false;
        }
    }
}
