package com.car2go.car2go_payment_service.payment.interfaces.rest.resources;

import com.car2go.car2go_payment_service.payment.domain.model.valueobjects.SubscriptionStatus;

public record SubscriptionResource(Integer price, String description, SubscriptionStatus status, Long profileId) {
}
