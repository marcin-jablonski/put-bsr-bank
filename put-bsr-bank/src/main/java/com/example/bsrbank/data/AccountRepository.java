package com.example.bsrbank.data;

import com.example.bsrbank.model.Account;
import com.example.bsrbank.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, String> {
    Account findByNo(String no);

    List<Account> findAccountsByUser(User user);

    boolean existsAccountByNo(String no);
}
