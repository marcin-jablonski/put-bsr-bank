package com.example.bsrbank.soap;

import com.bank.types.*;
import com.example.bsrbank.logic.AccountsService;
import com.example.bsrbank.logic.OperationsService;
import com.example.bsrbank.logic.UsersService;
import com.example.bsrbank.logic.ValidationService;
import com.example.bsrbank.logic.exceptions.*;
import com.example.bsrbank.model.Account;
import com.example.bsrbank.model.Transaction;
import com.example.bsrbank.model.User;
import com.example.bsrbank.rest.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.IOException;
import java.util.List;

@Endpoint
public class BankEndpoint {
    private static final String NAMESPACE_URI = "http://www.bank.com/types";

    @Autowired
    private UsersService usersService;

    @Autowired
    private AccountsService accountsService;

    @Autowired
    private OperationsService operationsService;

    @Autowired
    private ValidationService validationService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "LoginRequest")
    @ResponsePayload
    public LoginResponse login(@RequestPayload LoginRequest payload) {
        LoginResponse response = new LoginResponse();
        User user = usersService.getUser("Marcin");
        List<Account> userAccounts = accountsService.getUserAccounts(user);
        for (Account account :
                userAccounts) {
            response.getAccounts().add(account.getNo());
        }
        return response;

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "HistoryRequest")
    @ResponsePayload
    public HistoryResponse getHistory(@RequestPayload HistoryRequest payload) throws AccountNotFoundException, InvalidAccountException {
        validationService.validateAccountNumber(payload.getAccountNumber());
        HistoryResponse response = new HistoryResponse();

        List<Transaction> history = accountsService.getAccountHistory(payload.getAccountNumber());

        for (Transaction historyItem:
                history) {
            response.getHistory().add(Transaction.toHistoryEntry(historyItem));
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "TransferRequest")
    @ResponsePayload
    public TransferResponse makeTransfer(@RequestPayload TransferRequest payload) throws AccountNotFoundException, IOException, UnauthorizedException, ValidationException, InsufficientFundsException, OperationUnavailableException {
        validationService.validateTransferRequest(payload);
        TransferResponse response = new TransferResponse();
        operationsService.handleTransfer(payload);
        response.setMessage("Transfer accepted");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PaymentRequest")
    @ResponsePayload
    public PaymentResponse makePayment(@RequestPayload PaymentRequest payload) throws AccountNotFoundException, InvalidAccountException, InvalidAmountException {
        validationService.validatePaymentRequest(payload);
        PaymentResponse response = new PaymentResponse();
        operationsService.handlePayment(payload);
        response.setMessage("Payment accepted");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "WithdrawRequest")
    @ResponsePayload
    public WithdrawResponse makePayment(@RequestPayload WithdrawRequest payload) throws AccountNotFoundException, InvalidAccountException, InvalidAmountException, InsufficientFundsException {
        validationService.validateWithdrawRequest(payload);
        WithdrawResponse response = new WithdrawResponse();
        operationsService.handleWithdraw(payload);
        response.setMessage("Withdrawal accepted");
        return response;
    }
}
