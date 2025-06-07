package com.car2go.car2go_payment_service.payment.application.internal.services.queryservices;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Subscription;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetAllSubscriptionQuery;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetSubscriptionByIdQuery;
import com.car2go.car2go_payment_service.payment.domain.services.SubscriptionQueryService;
import com.car2go.car2go_payment_service.payment.infrastructure.persistence.jpa.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public List<Subscription> handle(GetAllSubscriptionQuery query) {
        return subscriptionRepository.findAll();
    }

    @Override
    public Optional<Subscription> handle(GetSubscriptionByIdQuery query) {
        return subscriptionRepository.findById(query.id());
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }
    @Override
    public Optional<Subscription> getByProfileId(Long profileId) {
        return subscriptionRepository.findByProfileId(profileId);
    }
}
