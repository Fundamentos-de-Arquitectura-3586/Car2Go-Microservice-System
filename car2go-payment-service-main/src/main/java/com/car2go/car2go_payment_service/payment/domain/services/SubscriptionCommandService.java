package com.car2go.car2go_payment_service.payment.domain.services;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Subscription;
import com.car2go.car2go_payment_service.payment.domain.model.commands.CreateSubscriptionCommand;
import com.car2go.car2go_payment_service.payment.domain.model.commands.UpdateSubscriptionCommand;

import java.util.Optional;

public interface SubscriptionCommandService {
    Optional<Subscription> handle(CreateSubscriptionCommand command);
    Optional<Subscription>handle(UpdateSubscriptionCommand command);
}