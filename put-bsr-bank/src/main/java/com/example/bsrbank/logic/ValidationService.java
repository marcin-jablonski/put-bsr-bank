package com.example.bsrbank.logic;

import com.bank.types.PaymentRequest;
import com.bank.types.TransferRequest;
import com.bank.types.WithdrawRequest;
import com.example.bsrbank.logic.exceptions.*;
import com.example.bsrbank.model.Account;
import com.example.bsrbank.model.User;
import org.iban4j.IbanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    @Autowired
    private AccountsService accountsService;

    public void validateTransferRequest(TransferRequest request, User user) throws InvalidAmountException, InvalidTitleException, InvalidAccountException, AccountNotFoundException, OperationUnavailableException {
        validateAccountOwner(request.getSourceAccount(), user);
        validateAccountNumber(request.getDestinationAccount());
        validateAccountNumber(request.getSourceAccount());
        validateTitle(request.getTitle());
        validateAmount(request.getAmount());
    }

    public void validatePaymentRequest(PaymentRequest request, User user) throws InvalidAccountException, InvalidAmountException, AccountNotFoundException, OperationUnavailableException {
        validateAccountOwner(request.getAccount(), user);
        validateAmount(request.getAmount());
        validateAccountNumber(request.getAccount());
    }

    public void validateWithdrawRequest(WithdrawRequest request, User user) throws InvalidAccountException, InvalidAmountException, AccountNotFoundException, OperationUnavailableException {
        validateAccountOwner(request.getAccount(), user);
        validateAmount(request.getAmount());
        validateAccountNumber(request.getAccount());
    }

    public void validateAccountNumber(String account) throws InvalidAccountException {
        if (account.isEmpty() || account.length() != 26) {
            throw new InvalidAccountException();
        }
        try {
            IbanUtil.validate("PL" + account);
        } catch (Exception e) {
            throw new InvalidAccountException();
        }
    }

    public void validateName(String name) throws InvalidNameException {
        if (name.isEmpty()) {
            throw new InvalidNameException();
        }
    }

    public void validateTitle(String title) throws InvalidTitleException {
        if (title.isEmpty() || title.length() > 255) {
            throw new InvalidTitleException();
        }
    }

    public void validateAmount(Integer amount) throws InvalidAmountException {
        if (amount < 1) {
            throw new InvalidAmountException();
        }
    }

    public void validateAccountOwner(String accountNumber, User user) throws OperationUnavailableException, AccountNotFoundException {
        Account account = accountsService.getAccount(accountNumber);
        if (!(account.getUser() == user)) {
            throw new OperationUnavailableException();
        }
    }
}
