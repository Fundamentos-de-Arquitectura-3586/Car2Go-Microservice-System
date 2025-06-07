package com.pe.platform.vehicle.interfaces.rest.dto;

public class VehicleSummaryDTO {
    private int id;
    private String name;
    private String brand;
    private String model;
    private String year;
    private double price;
    private String transmission;
    private String engine;
    private String status;

    public VehicleSummaryDTO(int id, String name, String brand, String model, String year, double price,
                             String transmission, String engine, String status) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.transmission = transmission;
        this.engine = engine;
        this.status = status;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getYear() { return year; }
    public double getPrice() { return price; }
    public String getTransmission() { return transmission; }
    public String getEngine() { return engine; }
    public String getStatus() { return status; }
}
