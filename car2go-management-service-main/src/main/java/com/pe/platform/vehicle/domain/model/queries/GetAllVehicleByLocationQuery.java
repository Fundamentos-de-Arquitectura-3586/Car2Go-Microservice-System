package com.pe.platform.vehicle.domain.model.queries;

public record GetAllVehicleByLocationQuery(String location) {
    public GetAllVehicleByLocationQuery {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("Location must not be null or empty");
        }
    }
}
