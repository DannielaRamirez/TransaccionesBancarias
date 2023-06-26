package com.bank.transactionbank.controller;


import com.bank.transactionbank.model.Account;
import com.bank.transactionbank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/account")
    public ResponseEntity<Account> createAccount(@RequestBody Account account ) {
        return  ResponseEntity.ok(accountService.createAccount(account));
    }

    @GetMapping("/findByAccount")
    public ResponseEntity<Account> findByAccountNumber(@RequestParam("accountNumber") String accountNumber) {
        Account account = accountService.findByAccountNumber(accountNumber);

        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<Optional<Account>> getAccount(@PathVariable String id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }


}
