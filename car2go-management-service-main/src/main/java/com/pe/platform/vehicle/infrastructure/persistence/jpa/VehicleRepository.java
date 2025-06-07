package com.pe.platform.vehicle.infrastructure.persistence.jpa;

import com.pe.platform.vehicle.domain.model.aggregates.Vehicle;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findAllByLocation(String location);

    Optional<Vehicle> findById(@NotNull Integer id);

    List<Vehicle> findByProfileId(Long profileId);

    List<Vehicle> findByName(String name);

    List<Vehicle> findAllVehiclesByProfileId(Long profileId);

    @Modifying
    @Transactional
    @Query("UPDATE Vehicle v SET v.name = :name, v.location = :location, v.brand = :brand, v.model = :model, v.color = :color, v.year = :year, v.transmission = :transmission, v.engine = :engine, v.mileage = :mileage, v.doors = :doors, v.plate = :plate, v.description = :description, v.price = :price, v.fuel = :fuel, v.speed = :speed WHERE v.id = :id")
    Optional<Vehicle> updateById(Integer id);
}
