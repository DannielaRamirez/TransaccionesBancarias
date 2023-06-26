package com.bank.transactionbank.repository;


import com.bank.transactionbank.model.Account;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@EnableScan
@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
    Account findByAccountNumber(String accountNumber);
}




