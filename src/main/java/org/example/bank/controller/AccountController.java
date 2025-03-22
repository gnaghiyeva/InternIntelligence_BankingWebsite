package org.example.bank.controller;

import org.example.bank.dtos.account.AccountCreateDto;
import org.example.bank.dtos.account.AccountDto;
import org.example.bank.dtos.account.AccountUpdateDto;
import org.example.bank.payload.ApiResponse;
import org.example.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createAccount(@ModelAttribute AccountCreateDto accountCreateDto) {
       ApiResponse response = accountService.createAccount(accountCreateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AccountDto>> getAccountByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse> getAccountByNumber(@PathVariable String accountNumber) {
        ApiResponse response = accountService.getAccountByNumber(accountNumber);
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{accountNumber}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> updateAccount(@PathVariable String accountNumber, @ModelAttribute AccountUpdateDto accountUpdateDto) {
        ApiResponse response = accountService.updateAccount(accountNumber, accountUpdateDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable String accountNumber) {
        ApiResponse response = accountService.deleteAccount(accountNumber);
        return ResponseEntity.ok(response);
    }
}
