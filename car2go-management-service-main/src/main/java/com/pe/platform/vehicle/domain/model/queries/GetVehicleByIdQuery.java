package com.pe.platform.vehicle.domain.model.queries;

public record GetVehicleByIdQuery(Integer id) {
    public GetVehicleByIdQuery {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be greater than zero");
        }
    }
}
