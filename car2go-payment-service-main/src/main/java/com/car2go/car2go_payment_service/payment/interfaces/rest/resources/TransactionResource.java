package com.car2go.car2go_payment_service.payment.interfaces.rest.resources;

import java.time.LocalDateTime;

public record TransactionResource(
    Long id,
    Long buyerId,     // En lugar de Profile buyer
    Long sellerId,    // En lugar de Profile seller  
    Long vehicleId,   // En lugar de Vehicle vehicle
    Double amount,
    String paymentStatus,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}