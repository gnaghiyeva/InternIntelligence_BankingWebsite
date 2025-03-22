package org.example.bank.service.impl;

import org.example.bank.model.Transaction;
import org.example.bank.repository.TransactionRepository;
import org.example.bank.service.FraudDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FraudDetectionServiceImpl implements FraudDetectionService {
    private static final BigDecimal DAILY_LIMIT = new BigDecimal("5000");
    private static final int MAX_TRANSACTIONS_PER_MINUTE = 3;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public boolean isFraudulentTransaction(Transaction transaction) {
        Long senderId = transaction.getSenderAccountNumber().getId();
        BigDecimal transactionAmount = transaction.getAmount();
        LocalDateTime now = LocalDateTime.now();

        // 🔍 1️⃣ Günlük toplam işlem tutarını kontrol et
        BigDecimal dailyTotal = transactionRepository.findTotalAmountBySenderToday(senderId, now.toLocalDate());
        if (dailyTotal.add(transactionAmount).compareTo(DAILY_LIMIT) > 0) {
            return true;
        }

        // 🔍 2️⃣ Hızlı işlem kontrolü (son 1 dakika içinde yapılan işlemleri kontrol et)
        List<Transaction> recentTransactions = transactionRepository.findRecentTransactionsBySender(senderId, now.minusMinutes(1));
        if (recentTransactions.size() >= MAX_TRANSACTIONS_PER_MINUTE) {
            return true;
        }

        return false;

    }
}
