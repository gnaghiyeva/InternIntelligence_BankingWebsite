package org.example.bank.service.impl;

import org.example.bank.dtos.account.AccountCreateDto;
import org.example.bank.dtos.account.AccountDto;
import org.example.bank.dtos.account.AccountUpdateDto;
import org.example.bank.model.Account;
import org.example.bank.model.User;
import org.example.bank.payload.ApiResponse;
import org.example.bank.repository.AccountRepository;
import org.example.bank.repository.UserRepository;
import org.example.bank.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponse createAccount(AccountCreateDto accountCreateDto) {
        User user = userRepository.findById(accountCreateDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setAccountNumber(accountCreateDto.getAccountNumber());
        account.setBalance(accountCreateDto.getBalance());
        account.setUser(user);
        accountRepository.save(account);
        return new ApiResponse(true, "Account created");
    }

    @Override
    public List<AccountDto> getAccountsByUserId(Long userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accounts.stream().map(account -> modelMapper.map(account, AccountDto.class)).collect(Collectors.toList());

    }

    @Override
    public ApiResponse getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        User user = account.getUser();

        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                user.getId(),
                user.getEmail()
        );

        return new ApiResponse(true, "Account retrieved", accountDto);
    }

    @Override
    public ApiResponse updateAccount(String accountNumber, AccountUpdateDto accountUpdateDto) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() != null) {
            account.setBalance(accountUpdateDto.getBalance());
        }
        accountRepository.save(account);

        return new ApiResponse(true, "Account updated successfully");
    }

    @Override
    public ApiResponse deleteAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.delete(account);
        return new ApiResponse(true, "Account deleted successfully");
    }


}
