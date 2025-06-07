package com.car2go.car2go_payment_service.payment.infrastructure.persistence.jpa;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByBuyerId(Long buyerId);
    List<Transaction> findAllBySellerId(Long sellerId);
    Optional<Transaction> findByIdAndBuyerId(Long transactionId, Long buyerId);
    Optional<Transaction> findByIdAndSellerId(Long transactionId, Long sellerId);
}
