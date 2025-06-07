package com.car2go.car2go_payment_service.payment.interfaces.rest;

import com.car2go.car2go_payment_service.payment.domain.model.aggregates.Transaction;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetAllTransactionsQuery;
import com.car2go.car2go_payment_service.payment.domain.model.queries.GetTransactionByIdQuery;
import com.car2go.car2go_payment_service.payment.domain.services.TransactionCommandService;
import com.car2go.car2go_payment_service.payment.domain.services.TransactionQueryService;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.CreateTransactionResource;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.TransactionResource;
import com.car2go.car2go_payment_service.payment.interfaces.rest.resources.UpdateTransactionResource;
import com.car2go.car2go_payment_service.payment.interfaces.rest.transform.CreateTransactionCommandFromResourceAssembler;
import com.car2go.car2go_payment_service.payment.interfaces.rest.transform.TransactionResourceFromEntityAssembler;
import com.car2go.car2go_payment_service.payment.interfaces.rest.transform.UpdateTransactionCommandFromResourceAssembler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/api/v1/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {
    private final TransactionQueryService transactionQueryService;
    private final TransactionCommandService transactionCommandService;

    public TransactionController(TransactionQueryService transactionQueryService,
                                 TransactionCommandService transactionCommandService) {
        this.transactionQueryService = transactionQueryService;
        this.transactionCommandService = transactionCommandService;
    }

    @PostMapping
    public ResponseEntity<TransactionResource> createTransaction(@RequestBody CreateTransactionResource resource) {
        var command = CreateTransactionCommandFromResourceAssembler.toCommandFromResource(resource);

        Optional<Transaction> transaction = transactionCommandService.handle(command);
        return transaction.map(resp ->
                        new ResponseEntity<>(TransactionResourceFromEntityAssembler.toResourceFromEntity(resp), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResource> getTransaction(@PathVariable Long transactionId) {
        var getTransactionByIdQuery = new GetTransactionByIdQuery(transactionId);
        var transaction = transactionQueryService.handle(getTransactionByIdQuery);
        if(transaction.isEmpty()) return ResponseEntity.badRequest().build();
        var transactionResource = TransactionResourceFromEntityAssembler.toResourceFromEntity(transaction.get());
        return ResponseEntity.ok(transactionResource);
    }

    @GetMapping("/me")
    public ResponseEntity<List<TransactionResource>> getMyTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // SIMPLIFIED: Use username instead of UserDetailsImpl for microservice isolation
        String username = authentication.getName();

        var getAllTransactionsQuery = new GetAllTransactionsQuery();

        var transactions = transactionQueryService.handle(getAllTransactionsQuery);

        var transactionResources = transactions.stream()
                .map(TransactionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(transactionResources);
    }


    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionResource> updateTransaction(@PathVariable Long transactionId, @RequestBody UpdateTransactionResource resource) {
        var command = UpdateTransactionCommandFromResourceAssembler.toCommandFromResource(resource);

        Optional<Transaction> transaction = transactionCommandService.handle(command);
        return transaction.map(resp ->
                        new ResponseEntity<>(TransactionResourceFromEntityAssembler.toResourceFromEntity(resp), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
