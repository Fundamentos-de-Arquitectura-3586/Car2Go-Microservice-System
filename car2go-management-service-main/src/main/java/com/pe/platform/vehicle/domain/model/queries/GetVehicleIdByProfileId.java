package com.pe.platform.vehicle.domain.model.queries;

public record GetVehicleIdByProfileId(long profileId) {

    public long profileId() {
        return profileId;
    }
}
