package com.bank.transactionbank.service;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.invoke.LambdaInvokerFactory;
import com.amazonaws.services.lambda.model.AWSLambdaException;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.bank.transactionbank.model.Transaction;
import com.bank.transactionbank.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Configuration
@Service
public class TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionService.class);


    @Value("${bankInfo.daviviendaSwift}")
    private String daviviendaSwift;

    @Value("${bankInfo.daviviendaUrl}")
    private String daviviendaUrl;

    @Value("${bankInfo.bancolombiaSwift}")
    private String bancolombiaSwift;

    @Value("${bankInfo.bancolombiaUrl}")
    private String bancolombiaUrl;

    @Value("${bankInfo.pichinchaSwift}")
    private String pichinchaSwith;


    @Autowired
    TransactionRepository transactionRepo;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }


    public void makeTransfer(Transaction transaction) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonPayload = objectMapper.writeValueAsString(transaction);
            //Las url y datos del banco se pueden consultar de bd para la demo las consultamos de properties
            transaction.getTransfer().getSenderBank().setBankBankCode(pichinchaSwith);
            if(transaction.getTransfer().getRecipientBank().equals("Davivienda")){
                transaction.getTransfer().getRecipientBank().setBankBankCode(daviviendaSwift);
                transaction.getTransfer().getRecipientBank().setUrlBank(daviviendaUrl);
            }else if(transaction.getTransfer().getRecipientBank().equals("Bancolombia")){
                transaction.getTransfer().getRecipientBank().setBankBankCode(bancolombiaSwift);
                transaction.getTransfer().getRecipientBank().setUrlBank(bancolombiaUrl);
            }
            callLambdaTransfer(jsonPayload);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }


    private void callLambdaTransfer(String jsonPayload) {
        try {
            AWSLambda lambdaClient = AWSLambdaClientBuilder.defaultClient();
            TransferLambda transferLambda = LambdaInvokerFactory.builder()
                    .lambdaClient(lambdaClient)
                    .build(TransferLambda.class);

            InvokeRequest invokeRequest = new InvokeRequest()
                    .withFunctionName("TransactionTransfer-dev-transaction")
                    .withPayload(jsonPayload);
            InvokeResult invokeResult = lambdaClient.invoke(invokeRequest);

            ByteBuffer responsePayloadBuffer = invokeResult.getPayload();

            if (responsePayloadBuffer != null) {
                String responsePayload = StandardCharsets.UTF_8.decode(responsePayloadBuffer).toString();
                System.out.println("Response: " + responsePayload);
            } else {
                System.out.println("No response payload received");
            }
        } catch (AWSLambdaException e) {
            logger.error("Error invoking Lambda function: " + e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage());
        }
    }

}


