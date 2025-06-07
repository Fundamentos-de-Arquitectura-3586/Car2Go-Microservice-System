package com.pe.platform.vehicle.interfaces.rest.transform;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import com.pe.platform.vehicle.interfaces.rest.resources.VehicleResource;

public class VehicleResourceFromEntityAssembler {

    public static VehicleResource toResourceFromEntity(Vehicle entity) {
        return new VehicleResource(
                entity.getId(),
                entity.getName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getBrand(),
                entity.getModel(),
                entity.getColor(),
                entity.getYear(),
                entity.getPrice(),
                entity.getTransmission(),
                entity.getEngine(),
                entity.getMileage(),
                entity.getDoors(),
                entity.getPlate(),
                entity.getLocation(),
                entity.getDescription(),
                entity.getImages(),
                entity.getProfileId(),
                entity.getFuel(),
                entity.getSpeed(),
                entity.getStatus().name()
        );
    }
}
