package com.bank.transactionbank.service;

import com.amazonaws.services.lambda.invoke.LambdaFunction;
import com.bank.transactionbank.model.Transaction;

public interface TransferLambda {
    @LambdaFunction(functionName = "TransactionTransfer-dev")
    void transfer(Transaction transaction);
}

