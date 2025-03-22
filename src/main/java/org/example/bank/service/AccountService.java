package org.example.bank.service;

import org.example.bank.dtos.account.AccountCreateDto;
import org.example.bank.dtos.account.AccountDto;
import org.example.bank.dtos.account.AccountUpdateDto;
import org.example.bank.payload.ApiResponse;

import java.util.List;

public interface AccountService {
    ApiResponse createAccount(AccountCreateDto accountCreateDto);

    List<AccountDto> getAccountsByUserId(Long userId);

    ApiResponse getAccountByNumber(String accountNumber);

    ApiResponse updateAccount(String accountNumber, AccountUpdateDto accountUpdateDto);

    ApiResponse deleteAccount(String accountNumber);
}
