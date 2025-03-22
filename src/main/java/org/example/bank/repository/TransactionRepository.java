package org.example.bank.repository;

import org.example.bank.model.Account;
import org.example.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderAccountNumber_AccountNumberOrReceiverAccountNumber_AccountNumber(String senderAccountNumber, String receiverAccountNumber);
}


