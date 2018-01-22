package com.example.bsrbank.logic;

import com.bank.types.PaymentRequest;
import com.bank.types.TransferRequest;
import com.bank.types.WithdrawRequest;
import com.example.bsrbank.logic.exceptions.InvalidAccountException;
import com.example.bsrbank.logic.exceptions.InvalidAmountException;
import com.example.bsrbank.logic.exceptions.InvalidNameException;
import com.example.bsrbank.logic.exceptions.InvalidTitleException;
import org.iban4j.IbanUtil;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    public void validateTransferRequest(TransferRequest request) throws InvalidAmountException, InvalidTitleException, InvalidAccountException {
        validateAccountNumber(request.getDestinationAccount());
        validateAccountNumber(request.getSourceAccount());
        validateTitle(request.getTitle());
        validateAmount(request.getAmount());
    }

    public void validatePaymentRequest(PaymentRequest request) throws InvalidAccountException, InvalidAmountException {
        validateAmount(request.getAmount());
        validateAccountNumber(request.getAccount());
    }

    public void validateWithdrawRequest(WithdrawRequest request) throws InvalidAccountException, InvalidAmountException {
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
}
