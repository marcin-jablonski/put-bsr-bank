package com.example.bsrbank.soap;

import com.bank.types.*;
import com.example.bsrbank.logic.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class BankEndpoint {
    private static final String NAMESPACE_URI = "http://www.bank.com/types";

    @Autowired
    private AccountsService accountsService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "historyRequest")
    @ResponsePayload
    public HistoryResponseType history(@RequestPayload HistoryRequestType payload) {

        HistoryResponseType historyResponse = new HistoryResponseType();
        HistoryEntryType etry = new HistoryEntryType();
        etry.setAmount(1000);
        etry.setBalance(0);
        etry.setSource("ABD");
        etry.setTitle("TEST");
        historyResponse.getHistoryEntry().add(etry);
        return historyResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "paymentRequest")
    @ResponsePayload
    public OperationResponseType payment(@RequestPayload PaymentRequestType payload) {

        OperationResponseType historyResponse = new OperationResponseType();

        return historyResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "withdrawRequest")
    @ResponsePayload
    public OperationResponseType withdraw(@RequestPayload WithdrawRequestType payload) {

        OperationResponseType historyResponse = new OperationResponseType();

        return historyResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "transferRequest")
    @ResponsePayload
    public OperationResponseType transfer(@RequestPayload TransferRequestType payload) {

        OperationResponseType historyResponse = new OperationResponseType();

        return historyResponse;
    }
}
