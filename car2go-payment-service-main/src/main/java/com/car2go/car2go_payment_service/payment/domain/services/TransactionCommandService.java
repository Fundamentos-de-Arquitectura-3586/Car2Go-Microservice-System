package com.car2go.car2go_payment_service.payment.domain.services;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Transaction;
import com.car2go.car2go_payment_service.payment.domain.model.commands.CreateTransactionCommand;
import com.car2go.car2go_payment_service.payment.domain.model.commands.UpdateTransactionCommand;

import java.util.Optional;

public interface TransactionCommandService {
    Optional<Transaction> handle(CreateTransactionCommand command);
    Optional<Transaction> handle(UpdateTransactionCommand command);
}
