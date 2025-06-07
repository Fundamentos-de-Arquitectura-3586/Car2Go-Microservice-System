package com.pe.platform.vehicle.interfaces.rest.resources;

public record VehicleResource(
        int id,
        String name,
        String phone,
        String email,
        String brand,
        String model,
        String color,
        String year,
        double price,
        String transmission,
        String engine,
        double mileage,
        String doors,
        String plate,
        String location,
        String description,
        java.util.List<String> image,
        Long profileId,
        String fuel,
        int speed,
        String status
) {}
