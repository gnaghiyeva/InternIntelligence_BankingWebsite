package org.example.bank.service;

import org.example.bank.dtos.transaction.TransactionCreateDto;
import org.example.bank.dtos.transaction.TransactionDto;
import org.example.bank.payload.ApiResponse;

public interface TransactionService {
    ApiResponse createTransaction(TransactionCreateDto transactionCreateDto);
    ApiResponse getTransactionsByAccount(String accountNumber);
}
