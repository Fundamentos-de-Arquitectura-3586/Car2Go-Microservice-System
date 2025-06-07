package com.pe.platform.vehicle.domain.services;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.domain.model.commands.UpdateVehicleCommand;

import java.util.Optional;

public interface VehicleCommandService {

    Optional<Vehicle> handle(CreateVehicleCommand command);

    Optional<Vehicle> handle(UpdateVehicleCommand command, int vehicleId);

    void deleteVehicle(int vehicleId, long userId);

    Optional<Vehicle> findById(int vehicleId);
}
