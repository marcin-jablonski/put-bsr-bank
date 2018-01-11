package com.example.bsrbank.rest.validation;

import com.example.bsrbank.logic.exceptions.InvalidAccountException;
import com.example.bsrbank.logic.exceptions.InvalidAmountException;
import com.example.bsrbank.logic.exceptions.InvalidNameException;
import com.example.bsrbank.logic.exceptions.InvalidTitleException;
import com.example.bsrbank.rest.model.InterBankTransfer;
import org.iban4j.IbanUtil;
import org.springframework.stereotype.Service;

@Service
public class RequestValidator {
    public void validate(InterBankTransfer transfer) throws InvalidAccountException, InvalidAmountException, InvalidNameException, InvalidTitleException {
        validateAccount(transfer.getSource_account());
        validateAmount(transfer.getAmount());
        validateName(transfer.getName());
        validateTitle(transfer.getTitle());
    }

    private void validateAccount(String account) throws InvalidAccountException {
        if (account.isEmpty() || account.length() != 26) {
            throw new InvalidAccountException();
        }
        try {
            IbanUtil.validate("PL" + account);
        } catch (Exception e) {
            throw new InvalidAccountException();
        }
    }

    private void validateName(String name) throws InvalidNameException {
        if (name.isEmpty()) {
            throw new InvalidNameException();
        }
    }

    private void validateTitle(String title) throws InvalidTitleException {
        if (title.isEmpty() || title.length() > 255) {
            throw new InvalidTitleException();
        }
    }

    private void validateAmount(Integer amount) throws InvalidAmountException {
        if (amount < 1) {
            throw new InvalidAmountException();
        }
    }
}
