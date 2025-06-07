package com.pe.platform.vehicle.interfaces.acl;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.queries.GetVehicleIdByProfileId;
import com.pe.platform.vehicle.domain.services.VehicleQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleContextFacade {

    private final VehicleQueryService vehicleQueryService;

    public VehicleContextFacade(VehicleQueryService vehicleQueryService) {
        this.vehicleQueryService = vehicleQueryService;
    }

    public List<Long> findByProfileId(long userId) {
        List<Vehicle> vehicles = vehicleQueryService.handle(new GetVehicleIdByProfileId(userId));

        return vehicles.stream()
                .map(vehicle -> Long.valueOf(vehicle.getId()))
                .collect(Collectors.toList());
    }
}
