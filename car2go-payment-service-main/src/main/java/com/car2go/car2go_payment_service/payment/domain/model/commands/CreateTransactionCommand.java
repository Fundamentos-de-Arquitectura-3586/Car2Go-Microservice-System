package com.car2go.car2go_payment_service.payment.domain.model.commands;

public record CreateTransactionCommand(Long buyerId, Long sellerId, Long vehicleId, Double amount) {
    public CreateTransactionCommand {
        if (vehicleId == null || vehicleId <= 0) {
            throw new IllegalArgumentException("VehicleId cannot be null");
        }
        if (buyerId == null || buyerId <= 0) {
            throw new IllegalArgumentException("BuyerId cannot be null");
        }
        if (sellerId == null || sellerId <= 0) {
            throw new IllegalArgumentException("SellerId cannot be null");
        }
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}
