package com.car2go.car2go_payment_service.payment.domain.services;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Transaction;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetAllTransactionsQuery;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetTransactionByIdQuery;

import java.util.List;
import java.util.Optional;

public interface TransactionQueryService {
    List<Transaction> handle(GetAllTransactionsQuery query);
    Optional<Transaction> handle(GetTransactionByIdQuery query);
}
