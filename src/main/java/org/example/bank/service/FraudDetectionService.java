package org.example.bank.service;

import org.example.bank.model.Transaction;

public interface FraudDetectionService {
    boolean isFraudulentTransaction(Transaction transaction);
}
