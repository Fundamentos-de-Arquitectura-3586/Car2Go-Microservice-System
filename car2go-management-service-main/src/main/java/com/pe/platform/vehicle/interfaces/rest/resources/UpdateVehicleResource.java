package com.pe.platform.vehicle.interfaces.rest.resources;

import java.util.List;

public record UpdateVehicleResource(
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
        int mileage,
        String doors,
        String plate,
        String location,
        String description,
        List<String> images,
        String fuel,
        int speed,
        String status
) {

    public UpdateVehicleResource {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Vehicle name cannot be null or empty");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand cannot be null or empty");
        }
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be null or empty");
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color cannot be null or empty");
        }
        if (year == null || year.isBlank()) {
            throw new IllegalArgumentException("Year cannot be null or empty");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        if (transmission == null || transmission.isBlank()) {
            throw new IllegalArgumentException("Transmission cannot be null or empty");
        }
        if (engine == null || engine.isBlank()) {
            throw new IllegalArgumentException("Engine cannot be null or empty");
        }
        if (mileage <= 0) {
            throw new IllegalArgumentException("Mileage must be greater than zero");
        }
        if (doors == null || doors.isBlank()) {
            throw new IllegalArgumentException("Doors cannot be null or empty");
        }
        if (plate == null || plate.isBlank()) {
            throw new IllegalArgumentException("Plate cannot be null or empty");
        }
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (images == null || images.isEmpty()) {  // Validación de lista de imágenes
            throw new IllegalArgumentException("At least one image is required");
        }
        if (fuel == null || fuel.isBlank()) {
            throw new IllegalArgumentException("Fuel type cannot be null or empty");
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("Speed must be greater than zero");
        }
        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
    }
}
