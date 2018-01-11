package com.example.bsrbank.data;

import com.example.bsrbank.model.Account;
import com.example.bsrbank.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HistoryRepository extends CrudRepository<Transaction, Integer> {
    List<Transaction> findByAccount(Account account);
}
