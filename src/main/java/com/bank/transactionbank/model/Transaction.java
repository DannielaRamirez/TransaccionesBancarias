package com.bank.transactionbank.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName="Transaction")
public class Transaction {
    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBAttribute
    private String accountNumber;
    @DynamoDBAttribute
    private String transactionType;
    @DynamoDBAttribute
    private Double amount;

    @DynamoDBAttribute(attributeName = "transfer")
    private Transfer transfer;
}
