package com.car2go.car2go_payment_service.payment.infrastructure.persistence.jpa;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByProfileId(Long profileId);
}
