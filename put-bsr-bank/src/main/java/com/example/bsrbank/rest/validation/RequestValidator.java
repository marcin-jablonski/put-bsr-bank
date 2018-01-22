package com.example.bsrbank.rest.validation;

import com.example.bsrbank.logic.ValidationService;
import com.example.bsrbank.logic.exceptions.InvalidAccountException;
import com.example.bsrbank.logic.exceptions.InvalidAmountException;
import com.example.bsrbank.logic.exceptions.InvalidNameException;
import com.example.bsrbank.logic.exceptions.InvalidTitleException;
import com.example.bsrbank.rest.model.InterBankTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestValidator {

    @Autowired
    private ValidationService validationService;

    public void validateIncoming(InterBankTransfer transfer) throws InvalidAccountException, InvalidAmountException, InvalidNameException, InvalidTitleException {
        validationService.validateAccountNumber(transfer.getSource_account());
        validationService.validateAmount(transfer.getAmount());
        validationService.validateName(transfer.getName());
        validationService.validateTitle(transfer.getTitle());
    }
}
