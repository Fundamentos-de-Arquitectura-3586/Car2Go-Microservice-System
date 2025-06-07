package com.car2go.car2go_payment_service.payment.domain.model.commands;

import com.car2go.car2go_payment_service.payment.domain.model.valueobjects.SubscriptionStatus;

public record UpdateSubscriptionCommand(Long profileId, SubscriptionStatus status) {
}
