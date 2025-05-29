package tech.wvs.desafiopicpay.transaction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.wvs.desafiopicpay.transaction.Transaction;
import tech.wvs.desafiopicpay.transaction.TransactionService;

import java.util.List;

@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/transfer")
    public ResponseEntity<Transaction> transfer(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.create(transaction));
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }
}
