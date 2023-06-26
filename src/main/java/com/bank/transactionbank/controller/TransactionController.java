package com.bank.transactionbank.controller;


import com.bank.transactionbank.exception.ExceptionResponse;
import com.bank.transactionbank.exception.InsufficientBalanceException;
import com.bank.transactionbank.model.Account;
import com.bank.transactionbank.model.Transaction;
import com.bank.transactionbank.service.AccountService;
import com.bank.transactionbank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    @PostMapping("/transaction")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        Transaction savedTransaction = transactionService.createTransaction(transaction);

        // Obtener la cuenta asociada a la transacción
        Account account = accountService.findByAccountNumber(transaction.getAccountNumber());
        String typeTx = transaction.getTransactionType();
        // Actualizar el balance de la cuenta según el tipo de transacción
        if (typeTx.equals("DEP")) {
            account.setBalance(account.getBalance() + transaction.getAmount());
            accountService.updateAccount(account);
        } else if (typeTx.equals("RET") || typeTx.equals("TRA")) {
            try {
                updateBalance(account, transaction, typeTx);
            } catch (InsufficientBalanceException e) {
                ExceptionResponse response = new ExceptionResponse(new Date(), e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

            }
        }
        return ResponseEntity.ok(savedTransaction);
    }

    private void updateBalance(Account account, Transaction transaction, String typeTx) {
        if (account.getBalance() < transaction.getAmount()) {
            if (typeTx.equals("RET")) {
                throw new InsufficientBalanceException("Saldo insuficiente para realizar el retiro");
            } else {
                throw new InsufficientBalanceException("Saldo insuficiente para realizar transferencia");
            }
        } else {
            account.setBalance(account.getBalance() - transaction.getAmount());
            if (typeTx.equals("TRA"))
                transactionService.makeTransfer(transaction);
                accountService.updateAccount(account);
        }
    }
}
