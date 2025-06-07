package com.car2go.car2go_payment_service.payment.interfaces.rest.resources;

import java.util.Date;

public record CreateTransactionResource(Long buyerId, Long sellerId, Long vehicleId, Double amount) {
}
