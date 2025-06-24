package com.pe.platform.vehicle.application.internal.services.commandservices;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.domain.model.commands.UpdateVehicleCommand;
import com.pe.platform.vehicle.domain.services.VehicleCommandService;
import com.pe.platform.vehicle.infrastructure.persistence.jpa.VehicleRepository;
import jakarta.transaction.Transactional;
import com.pe.platform.vehicle.application.internal.services.outboundservice.VehicleService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {

    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;

    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository, VehicleService vehicleService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleService = vehicleService;
    }

    @Override
    public Optional<Vehicle> handle(CreateVehicleCommand command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("Unauthorized");
        }
        
        String userId = authentication.getPrincipal().toString();
        
        // Solo validamos que el usuario existe
        if (!vehicleService.validateUserExists(userId)) {
            throw new IllegalStateException("User not found or not authorized");
        }

        try {
            long userIdLong = Long.parseLong(userId);
            // Usamos directamente el ID del usuario del token            
            var newVehicle = new Vehicle(command);
            newVehicle.setProfileId(userIdLong);
            var createdVehicle = vehicleRepository.save(newVehicle);
            return Optional.of(createdVehicle);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Invalid user ID in token");
        }
    }

    @Transactional
    @Override
    public Optional<Vehicle> handle(UpdateVehicleCommand command, int vehicleId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalStateException("Unauthorized");
        }

        String userId = authentication.getPrincipal().toString();
        if (!vehicleService.validateUserExists(userId)) {
            throw new IllegalStateException("User not found or not authorized");
        }

        Long userIdLong;
        try {
            userIdLong = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Invalid user ID in token");
        }

        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);
        if (vehicleOptional.isEmpty() || vehicleOptional.get().getProfileId() != userIdLong) {
            throw new IllegalStateException("The vehicle does not exist or you do not have permission to update it.");
        }

        Vehicle vehicle = vehicleOptional.get();
        vehicle.updateVehicleInfo(command);
        vehicle.setProfileId(userIdLong);

        var updatedVehicle = vehicleRepository.save(vehicle);
        return Optional.of(updatedVehicle);
    }

    @Override
    @Transactional
    public void deleteVehicle(int vehicleId, long userId) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);
        
        if (!vehicleService.validateUserExists(String.valueOf(userId))) {
            throw new IllegalStateException("User not found when looking for your ID");
        }

        if (vehicleOptional.isEmpty() || vehicleOptional.get().getProfileId() != userId) {
            throw new IllegalStateException("The vehicle does not exist or you do not have permission to delete it.");
        }

        Vehicle vehicle = vehicleOptional.get();
        vehicleRepository.delete(vehicle);
    }

    @Override
    public Optional<Vehicle> findById(int vehicleId) {
        return vehicleRepository.findById(vehicleId);
    }
}
