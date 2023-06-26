package com.bank.transactionbank.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName="Account")
public class Account {

    @DynamoDBHashKey(attributeName = "id")
    private String id;
    @DynamoDBAttribute
    private String accountNumber;
    @DynamoDBAttribute
    private String document;
    @DynamoDBAttribute
    private String accountHolderName;
    @DynamoDBAttribute
    private String accountHolderLastName;
    @DynamoDBAttribute
    private String accountType; //Ahorros //Corriente
    @DynamoDBAttribute
    private Double initialBalance;
    @DynamoDBAttribute
    private Double balance;




}
