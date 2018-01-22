package com.example.bsrbank.rest.controllers;

import com.example.bsrbank.logic.AccountsService;
import com.example.bsrbank.logic.exceptions.*;
import com.example.bsrbank.rest.auth.AuthenticationService;
import com.example.bsrbank.rest.client.AccountClient;
import com.example.bsrbank.rest.exceptions.UnauthorizedException;
import com.example.bsrbank.rest.model.InterBankTransfer;
import com.example.bsrbank.model.Transaction;
import com.example.bsrbank.rest.exceptions.NotFoundException;
import com.example.bsrbank.rest.validation.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private RequestValidator requestValidator;

    @RequestMapping(value = "/accounts/{accountNumber}/history", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void postToHistory(@RequestHeader(value = "Authorization", required = false) String authorizationHeader, @PathVariable("accountNumber") String accountNumber, @RequestBody InterBankTransfer transfer) throws NotFoundException, UnauthorizedException, InvalidAccountException, InvalidAmountException, InvalidTitleException, InvalidNameException {
        authenticationService.checkCredentials(authorizationHeader);
        requestValidator.validateIncoming(transfer);
        Transaction tr = new Transaction(transfer.getAmount(), transfer.getTitle(), transfer.getSource_account());

        try {
            accountsService.addTransactionToHistory(accountNumber, tr);
        } catch (AccountNotFoundException e) {
            throw new NotFoundException();
        }
    }
}
