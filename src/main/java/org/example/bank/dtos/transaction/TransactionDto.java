package org.example.bank.dtos.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
}
