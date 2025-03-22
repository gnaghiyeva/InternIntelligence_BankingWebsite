package org.example.bank.service.impl;

import org.example.bank.dtos.transaction.TransactionCreateDto;
import org.example.bank.dtos.transaction.TransactionDto;
import org.example.bank.model.Account;
import org.example.bank.model.Transaction;
import org.example.bank.payload.ApiResponse;
import org.example.bank.repository.AccountRepository;
import org.example.bank.repository.TransactionRepository;
import org.example.bank.service.FraudDetectionService;
import org.example.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FraudDetectionService fraudDetectionService;
    @Override
    public ApiResponse createTransaction(TransactionCreateDto transactionCreateDto) {
        Account sender = accountRepository.findByAccountNumber(transactionCreateDto.getSenderAccountNumber())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account receiver = accountRepository.findByAccountNumber(transactionCreateDto.getReceiverAccountNumber())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (sender.getBalance().compareTo(transactionCreateDto.getAmount()) < 0) {
            return new ApiResponse(false, "You have insufficient balance to complete the transaction");
        }

        if (transactionCreateDto.getAmount().compareTo(receiver.getBalance()) > 0) {
            return new ApiResponse(false, "The amount to be sent cannot be greater than the receiver's balance");
        }

        Transaction transaction = new Transaction();

        transaction.setSenderAccountNumber(sender);
        transaction.setReceiverAccountNumber(receiver);
        transaction.setAmount(transactionCreateDto.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionCreateDto.getAmount()));
        receiver.setBalance(receiver.getBalance().add(transactionCreateDto.getAmount()));

        if (fraudDetectionService.isFraudulentTransaction(transaction)) {
            return new ApiResponse(false, "Transaction flagged as fraudulent!");
        }
        
        transactionRepository.save(transaction);
        accountRepository.save(sender);
        accountRepository.save(receiver);

        return new ApiResponse(true, "Transaction successful");
    }

    @Override
    public ApiResponse getTransactionsByAccount(String accountNumber) {
        List<Transaction> transactions = transactionRepository.findBySenderAccountNumber_AccountNumberOrReceiverAccountNumber_AccountNumber(accountNumber, accountNumber);
        if (transactions.isEmpty()) {
            return new ApiResponse(false, "No transactions found for account number: " + accountNumber);
        }
        List<TransactionDto> transactionDtos = transactions.stream().map(transaction ->
                new TransactionDto(
                        transaction.getId(),
                        transaction.getSenderAccountNumber().getAccountNumber(),
                        transaction.getReceiverAccountNumber().getAccountNumber(),
                        transaction.getAmount(),
                        transaction.getTransactionDate()
                )
        ).collect(Collectors.toList());

        return new ApiResponse(true, "Transactions retrieved successfully", transactionDtos);
    }

}
