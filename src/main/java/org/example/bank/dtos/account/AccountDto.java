package org.example.bank.dtos.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccountDto {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Long userId;
    public String firstName;
    public String lastName;



}
