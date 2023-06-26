package com.bank.transactionbank.repository;


import com.bank.transactionbank.model.Transaction;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@EnableScan
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {
}
