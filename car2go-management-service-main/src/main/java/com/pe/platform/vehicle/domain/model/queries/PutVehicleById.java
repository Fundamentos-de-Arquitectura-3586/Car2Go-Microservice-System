package com.pe.platform.vehicle.domain.model.queries;

public record PutVehicleById(Integer id) {
    public PutVehicleById {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        }
    }
}
