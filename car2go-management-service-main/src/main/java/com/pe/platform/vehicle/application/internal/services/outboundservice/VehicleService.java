package com.car2go.car2go_iam_service.iam.application.internal.outboundservices.acl;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    private final WebClient webClient;

    public VehicleService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://car2go-vehicle-service").build();
    }

    // Calls for other bounded services that are used by Vehicle
    public User getUserById(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();

        return webClient.get()
                .uri("/users/{id}", userId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
    
}
