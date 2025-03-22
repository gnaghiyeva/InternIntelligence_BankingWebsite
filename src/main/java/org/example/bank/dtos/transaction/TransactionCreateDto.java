package org.example.bank.dtos.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreateDto {
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal amount;
}
