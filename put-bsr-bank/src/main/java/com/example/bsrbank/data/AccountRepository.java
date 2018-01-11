package com.example.bsrbank.data;

import com.example.bsrbank.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {
    Account findByNo(String no);

    boolean existsAccountByNo(String no);
}
