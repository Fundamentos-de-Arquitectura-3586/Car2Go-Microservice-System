package com.car2go.car2go_payment_service.payment.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

import com.car2go.car2go_payment_service.payment.domain.model.valueobjects.PaymentStatus;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // EN LUGAR DE Profile objects, usar IDs
    @Column(name = "buyer_id")
    private Long buyerId;
    
    @Column(name = "seller_id") 
    private Long sellerId;
    
    // EN LUGAR DE Vehicle object, usar ID
    @Column(name = "vehicle_id")
    private Long vehicleId;
    
    @Column(name = "amount")
    private Double amount;
    
    @Column(name = "payment_status")
    private String paymentStatus;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructor vacío
    public Transaction() {}
    
    // Constructor con parámetros
    public Transaction(Long buyerId, Long sellerId, Long vehicleId, Double amount) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.vehicleId = vehicleId;
        this.amount = amount;
        this.paymentStatus = "PENDING";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setStatus(String status) {
    this.paymentStatus = status;
    }

    // O si tienes PaymentStatus enum:
    public void setStatus(PaymentStatus status) {
        this.paymentStatus = status.toString();
    }
    
    // Getters manuales para evitar problemas con Lombok
    public Long getId() { return id; }
    public Long getBuyerId() { return buyerId; }
    public Long getSellerId() { return sellerId; }
    public Long getVehicleId() { return vehicleId; }
    public Double getAmount() { return amount; }
    public String getPaymentStatus() { return paymentStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}