package org.example.bank.controller;

import org.example.bank.dtos.transaction.TransactionCreateDto;
import org.example.bank.payload.ApiResponse;
import org.example.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createTransaction(@ModelAttribute TransactionCreateDto transactionCreateDto) {
        return ResponseEntity.ok(transactionService.createTransaction(transactionCreateDto));
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<ApiResponse> getTransactions(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccount(accountNumber));
    }
}
