
package com.car2go.car2go_payment_service.payment.application.internal.services.outbundservices;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.car2go.car2go_payment_service.shared.Infrastructure.security.JwtTokenUtil;

@Service
public class PaymentService {
        private final WebClient webClientProfile;
        private final WebClient webClientVehicle;
        private static final String PROFILE_VEHICLE_NAME = "vehicle-service";
        private static final String PROFILE_SERVICE_NAME = "profile-service";



    public PaymentService(WebClient.Builder webClientBuilder) {
        this.webClientProfile = webClientBuilder
            .baseUrl("http://" +PROFILE_SERVICE_NAME + "/api/v1/users")
            .build();
        this.webClientVehicle = webClientBuilder
            .baseUrl("http://" +PROFILE_VEHICLE_NAME + "/api/v1/vehicle")
            .build();
    }


    public Vehicle getVehicleById(Long vehicleId) {
        try {
            String jwtToken = JwtTokenUtil.getCurrentJwtTokenWithBearer();
            
             WebClient.RequestHeadersSpec<?> request = webClientVehicle.get().uri("/{id}", vehicleId);
            
            // Agregar el JWT token si está disponible
            if (jwtToken != null) {
                request = request.header("Authorization", jwtToken);
            }
            return request.retrieve()
                    .bodyToMono(Vehicle.class)
                    .onErrorReturn(null)
                    .block();
        } catch (Exception e) {
            System.err.println("Error calling vehicle service: " + e.getMessage());
            return null;
        }
    }

    public User getUserById(Long userId) {
        try {
            String jwtToken = JwtTokenUtil.getCurrentJwtTokenWithBearer();
            
            WebClient.RequestHeadersSpec<?> request = webClientProfile.get().uri("/{id}", userId);
            
            // Agregar el JWT token si está disponible
            if (jwtToken != null) {
                request = request.header("Authorization", jwtToken);
            }
            
            return request.retrieve()
                .bodyToMono(User.class)
                .onErrorReturn(null)
                .block();
        } catch (Exception e) {
            System.err.println("Error calling IAM service: " + e.getMessage());
            return null;
        }
    }

    public boolean validateVehiclePurchase(Long vehicleId, Long buyerId) {
        Vehicle vehicle = getVehicleById(vehicleId);
        User buyer = getUserById(buyerId);
        
        if (vehicle == null || buyer == null) {
            return false;
        }
        
        return true;
    }

    // DTOs for communication
    public static class Vehicle {
        private Long id;
        private String model;
        private String brand;
        private Double price;
        private String status;
        private Long profileId;

        public Vehicle() {}

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        
        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }
        
        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public Long getProfileId() { return profileId; }
        public void setProfileId(Long profileId) { this.profileId = profileId; }
    }

    public static class User {
        private Long id;
        private String username;
        private String roles;

        public User() {}

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getRoles() { return roles; }
        public void setRoles(String roles) { this.roles = roles; }
    }
}

