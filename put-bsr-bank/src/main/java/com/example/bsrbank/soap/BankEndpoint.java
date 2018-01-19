package com.example.bsrbank.soap;

import com.bank.types.*;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class BankEndpoint {
    private static final String NAMESPACE_URI = "http://www.bank.com/types";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "HistoryRequest")
    @ResponsePayload
    public HistoryResponse getHistory(@RequestPayload HistoryRequest payload) {
        HistoryResponse response = new HistoryResponse();

        return response;
    }
}
