package com.car2go.car2go_payment_service.payment.interfaces.rest.resources;

import com.car2go.car2go_payment_service.payment.domain.model.valueobjects.PaymentStatus;

public record UpdateTransactionResource(Long transactionId, PaymentStatus status) {
}
