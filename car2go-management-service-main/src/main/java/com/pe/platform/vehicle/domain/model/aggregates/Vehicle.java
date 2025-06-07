package com.pe.platform.vehicle.domain.model.aggregates;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pe.platform.vehicle.common.converters.ListToStringConverter;
import com.pe.platform.vehicle.domain.model.commands.CreateVehicleCommand;
import com.pe.platform.vehicle.domain.model.commands.UpdateVehicleCommand;
import com.pe.platform.vehicle.domain.model.valueobjects.vehicleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String year;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String transmission;

    @Column(nullable = false)
    private String engine;

    @Column(nullable = false)
    private double mileage;

    @Column(nullable = false)
    private String doors;

    @Column(nullable = false)
    private String plate;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String description;

    @Convert(converter = ListToStringConverter.class)
    @Column(name = "images", columnDefinition = "LONGTEXT")
    private List<String> images;

    @Column(nullable = false)
    private String fuel;

    @Column(nullable = false)
    private int speed;

    @Column(nullable = false)
    private long profileId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private vehicleStatus status;

/* 
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Review> reviews;
*/
    protected Vehicle() {}

    public Vehicle(CreateVehicleCommand command) {
        this.name = command.name();
        this.phone = command.phone();
        this.email = command.email();
        this.brand = command.brand();
        this.model = command.model();
        this.color = command.color();
        this.year = command.year();
        this.price = command.price();
        this.transmission = command.transmission();
        this.engine = command.engine();
        this.mileage = command.mileage();
        this.doors = command.doors();
        this.plate = command.plate();
        this.location = command.location();
        this.description = command.description();
        this.images = command.images();
        this.fuel = command.fuel();
        this.speed = command.speed();
        this.status = vehicleStatus.PENDING;
    }

    public Vehicle updateVehicleInfo(UpdateVehicleCommand command) {
        this.name = command.name();
        this.phone = command.phone();
        this.email = command.email();
        this.brand = command.brand();
        this.model = command.model();
        this.color = command.color();
        this.year = command.year();
        this.price = command.price();
        this.transmission = command.transmission();
        this.engine = command.engine();
        this.mileage = command.mileage();
        this.doors = command.doors();
        this.plate = command.plate();
        this.location = command.location();
        this.description = command.description();
        this.images = command.images();
        this.fuel = command.fuel();
        this.speed = command.speed();

        if (command.status() != null) {
            this.status = command.status();
        }

        return this;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public long getProfileId() {
        return this.profileId;
    }

    public long getUserId() {
        return profileId;
    }

    public int getId() {
        return id;
    }

    public int getSpeed() {
        return speed;
    }

    public vehicleStatus getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getEngine() {
        return engine;
    }

    public double getMileage() {
        return mileage;
    }

    public String getDoors() {
        return doors;
    }

    public String getPlate() {
        return plate;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImages() {
        return images;
    }

    public String getFuel() {
        return fuel;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }
}



