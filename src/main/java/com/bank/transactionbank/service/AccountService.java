package com.bank.transactionbank.service;


import com.bank.transactionbank.exception.EntityNotFoundException;
import com.bank.transactionbank.model.Account;
import com.bank.transactionbank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepo;

    public Account createAccount(Account account) {
        return accountRepo.save(account);
    }

    public Account findByAccountNumber(String accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber);
    }

    public Optional<Account> getAccount(String id) {
        Optional<Account> account = accountRepo.findById(id);
        if (account.isPresent()) {
            return account;
        }
        else
            throw new EntityNotFoundException("No se encuentra cuenta con numero ingresado");
    }

    public Account updateAccount(Account account) {
        return accountRepo.save(account);
    }

}
