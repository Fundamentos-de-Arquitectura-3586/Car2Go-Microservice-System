package com.pe.platform.vehicle.interfaces.acl;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleIdByProfileId;
import com.pe.platform.vehicle.domain.services.VehicleQueryService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.pe.platform.vehicle.domain.model.queries.GetVehicleByIdQuery;

//NO SE USA

@Service
public class VehicleContextFacade {

    private final VehicleQueryService vehicleQueryService;

    public VehicleContextFacade(VehicleQueryService vehicleQueryService) {
        this.vehicleQueryService = vehicleQueryService;
    }
    /**
     * Fetches vehicle List by UserID for external bounded contexts
     * @param vehicleId The vehicle ID
     * @return Vehicle or null if not found
     */
    public List<Long> findByProfileId(long userId) {
        List<Vehicle> vehicles = vehicleQueryService.handle(new GetVehicleIdByProfileId(userId));

        return vehicles.stream()
                .map(vehicle -> Long.valueOf(vehicle.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Fetches vehicle by ID for external bounded contexts
     * @param vehicleId The vehicle ID
     * @return Vehicle or null if not found
     */
    public Vehicle getVehicleById(int vehicleId) {
        var query = new GetVehicleByIdQuery(vehicleId);
        var result = vehicleQueryService.handle(query);
        return result.orElse(null);
    }

    /**
     * Validates if vehicle exists
     * @param vehicleId The vehicle ID
     * @return true if exists, false otherwise
     */
    public boolean vehicleExists(int vehicleId) {
        return getVehicleById(vehicleId) != null;
    }

}
