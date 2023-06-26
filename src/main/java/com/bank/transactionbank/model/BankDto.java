package com.bank.transactionbank.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class BankDto {
    private String bankName;
    private String bankBankCode;
    private String accountNumber;
    private String document;
    private String accountHolderName;
    private String accountHolderLastName;
    private String accountType;
    private String urlBank;
}
