package com.bank.transactionbank.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName="Bank")
public class Bank {
    @DynamoDBAttribute
    private String bankName;
    @DynamoDBAttribute
    private String bankBankCode;
}
