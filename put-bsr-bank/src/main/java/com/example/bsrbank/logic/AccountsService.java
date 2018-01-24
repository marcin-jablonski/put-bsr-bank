package com.example.bsrbank.logic;

import com.example.bsrbank.data.AccountRepository;
import com.example.bsrbank.data.HistoryRepository;
import com.example.bsrbank.logic.exceptions.AccountAlreadyExistsException;
import com.example.bsrbank.logic.exceptions.AccountNotFoundException;
import com.example.bsrbank.model.Account;
import com.example.bsrbank.model.Transaction;
import com.example.bsrbank.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private HistoryRepository historyRepository;

    public boolean doesAccountExist(String accountNumber) {
        return accountRepository.existsAccountByNo(accountNumber);
    }

    public void updateAccount(Account account) throws AccountNotFoundException {
        if (!doesAccountExist(account.getNo())) {
            throw new AccountNotFoundException();
        }
        accountRepository.save(account);
    }

    public void addAccount(Account account) throws AccountAlreadyExistsException {
        if (doesAccountExist(account.getNo())) {
            throw new AccountAlreadyExistsException();
        }
        accountRepository.save(account);
    }

    public Account getAccount(String accountNumber) throws AccountNotFoundException {
        if (!doesAccountExist(accountNumber)) {
            throw new AccountNotFoundException();
        }
        return accountRepository.findByNo(accountNumber);
    }

    public List<Transaction> getAccountHistory(String accountNumber) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);

        return historyRepository.findByAccount(account);
    }

    public void addTransactionToHistory(String accountNumber, Transaction transaction) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);

        int currentBalance = account.getBalance();
        currentBalance += transaction.getAmount();
        account.setBalance(currentBalance);

        transaction.setAccount(account);
        transaction.setBalance(currentBalance);

        accountRepository.save(account);
        historyRepository.save(transaction);
    }

    public boolean accountHasEnoughBalance(String accountNumber, int amount) throws AccountNotFoundException {
        Account account = getAccount(accountNumber);

        return account.getBalance() >= amount;
    }

    public List<Account> getUserAccounts(User user) {
        return accountRepository.findAccountsByUser(user);
    }
}
