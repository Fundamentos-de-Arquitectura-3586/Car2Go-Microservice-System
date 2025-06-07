package com.car2go.car2go_payment_service.payment.domain.services;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Subscription;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetAllSubscriptionQuery;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetSubscriptionByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SubscriptionQueryService {
    List<Subscription> handle(GetAllSubscriptionQuery query);
    Optional<Subscription> handle(GetSubscriptionByIdQuery query);
    Optional<Subscription> getByProfileId(Long profileId);
    List<Subscription> getAllSubscriptions();

}
