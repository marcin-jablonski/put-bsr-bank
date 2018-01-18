package com.example.bsrbank.rest.client;

import com.example.bsrbank.logic.BanksService;
import com.example.bsrbank.logic.exceptions.AccountNotFoundException;
import com.example.bsrbank.logic.exceptions.ValidationException;
import com.example.bsrbank.rest.exceptions.UnauthorizedException;
import com.example.bsrbank.rest.model.InterBankTransfer;
import com.example.bsrbank.rest.model.InterBankTransferError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class AccountClient {

    @Autowired
    private BanksService banksService;

    public void interBankTransferRequest(InterBankTransfer transfer, String destinationAccount) throws AccountNotFoundException, UnauthorizedException, ValidationException, IOException {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate template = builder.basicAuthorization("admin", "admin").build();

        String url = banksService.getBankUrl(banksService.getBankId(destinationAccount)) + "accounts/" + destinationAccount + "/history";

        try {
            ResponseEntity<Void> response = template.postForEntity(url, transfer, Void.class);
        } catch (HttpClientErrorException e) {
            HttpStatus status = e.getStatusCode();
            if (status == HttpStatus.NOT_FOUND) {
                throw new AccountNotFoundException();
            } else if (status == HttpStatus.UNAUTHORIZED) {
                throw new UnauthorizedException();
            } else if (status == HttpStatus.BAD_REQUEST) {
                ObjectMapper objectMapper = new ObjectMapper();

                InterBankTransferError error = objectMapper.readValue(e.getResponseBodyAsString(), InterBankTransferError.class);

                throw new ValidationException();
            }
        }

    }
}
