package org.example.bank.repository;

import org.example.bank.model.Account;
import org.example.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderAccountNumber_AccountNumberOrReceiverAccountNumber_AccountNumber(String senderAccountNumber, String receiverAccountNumber);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.senderAccountNumber.id = :senderId AND DATE(t.transactionDate) = :today")
    BigDecimal findTotalAmountBySenderToday(Long senderId, LocalDate today);

    @Query("SELECT t FROM Transaction t WHERE t.senderAccountNumber.id = :senderId AND t.transactionDate >= :oneMinuteAgo")
    List<Transaction> findRecentTransactionsBySender(Long senderId, LocalDateTime oneMinuteAgo);

    void deleteBySenderAccountNumberOrReceiverAccountNumber(Account sender, Account receiver);
}


