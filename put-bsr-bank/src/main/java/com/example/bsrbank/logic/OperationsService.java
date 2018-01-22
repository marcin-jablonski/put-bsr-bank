package com.example.bsrbank.logic;

import com.bank.types.PaymentRequest;
import com.bank.types.TransferRequest;
import com.bank.types.WithdrawRequest;
import com.example.bsrbank.logic.exceptions.AccountNotFoundException;
import com.example.bsrbank.logic.exceptions.InsufficientFundsException;
import com.example.bsrbank.logic.exceptions.ValidationException;
import com.example.bsrbank.model.Transaction;
import com.example.bsrbank.rest.client.AccountClient;
import com.example.bsrbank.rest.exceptions.UnauthorizedException;
import com.example.bsrbank.rest.model.InterBankTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OperationsService {
    @Autowired
    private AccountsService accountsService;

    @Autowired
    private AccountClient accountClient;

    public void handleTransfer(TransferRequest transferRequest) throws AccountNotFoundException, UnauthorizedException, IOException, ValidationException, InsufficientFundsException {
        if (!accountsService.accountHasEnoughBalance(transferRequest.getSourceAccount(), transferRequest.getAmount())) {
            throw new InsufficientFundsException();
        }

        if (transferRequest.getDestinationAccount().substring(2, 10).equals(BanksService.BANK_ID)) {
            handleInternalTransfer(transferRequest);
        } else {
            handleExternalTransfer(transferRequest);
        }
    }

    private void handleInternalTransfer(TransferRequest transferRequest) throws AccountNotFoundException {
        Transaction incomingTransaction = new Transaction();
        incomingTransaction.setAmount(transferRequest.getAmount());
        incomingTransaction.setTitle(transferRequest.getTitle());
        incomingTransaction.setSource(transferRequest.getSourceAccount());

        Transaction outgoingTransaction = new Transaction();
        outgoingTransaction.setAmount(-transferRequest.getAmount());
        outgoingTransaction.setTitle(transferRequest.getTitle());
        outgoingTransaction.setSource(transferRequest.getDestinationAccount());

        accountsService.addTransactionToHistory(transferRequest.getSourceAccount(), outgoingTransaction);
        accountsService.addTransactionToHistory(transferRequest.getDestinationAccount(), incomingTransaction);
    }

    private void handleExternalTransfer(TransferRequest transferRequest) throws AccountNotFoundException, IOException, UnauthorizedException, ValidationException {
        InterBankTransfer interBankTransfer = new InterBankTransfer();
        interBankTransfer.setAmount(transferRequest.getAmount());
        interBankTransfer.setSource_account(transferRequest.getSourceAccount());
        interBankTransfer.setTitle(transferRequest.getTitle());
        interBankTransfer.setName("What goes here?");

        accountClient.interBankTransferRequest(interBankTransfer, transferRequest.getDestinationAccount());

        Transaction outgoingTransaction = new Transaction();
        outgoingTransaction.setAmount(-transferRequest.getAmount());
        outgoingTransaction.setTitle(transferRequest.getTitle());
        outgoingTransaction.setSource(transferRequest.getDestinationAccount());

        accountsService.addTransactionToHistory(transferRequest.getSourceAccount(), outgoingTransaction);
    }

    public void handlePayment(PaymentRequest paymentRequest) throws AccountNotFoundException {
        Transaction payment = new Transaction();
        payment.setAmount(paymentRequest.getAmount());
        payment.setTitle("PAYMENT");
        payment.setSource("PAYMENT");

        accountsService.addTransactionToHistory(paymentRequest.getAccount(), payment);
    }

    public void handleWithdraw(WithdrawRequest withdrawRequest) throws AccountNotFoundException, InsufficientFundsException {
        if (!accountsService.accountHasEnoughBalance(withdrawRequest.getAccount(), withdrawRequest.getAmount())) {
            throw new InsufficientFundsException();
        }
        Transaction payment = new Transaction();
        payment.setAmount(-withdrawRequest.getAmount());
        payment.setTitle("WITHDRAW");
        payment.setSource("WITHDRAW");

        accountsService.addTransactionToHistory(withdrawRequest.getAccount(), payment);
    }
}
