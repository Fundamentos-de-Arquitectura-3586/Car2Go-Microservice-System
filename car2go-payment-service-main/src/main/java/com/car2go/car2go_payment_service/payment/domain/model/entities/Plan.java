package com.car2go.car2go_payment_service.payment.domain.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "plans")
public class Plan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "price")
    private Double price;
    
    @Column(name = "description")
    private String description;
    
    // Constructors
    public Plan() {}

    // AGREGAR ESTE CONSTRUCTOR:
    public Plan(String name, Double price) {
        this.name = name;
        this.price = price;
        this.description = ""; // Valor por defecto
    }

    public Plan(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
    
    // Getters y Setters manuales
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}