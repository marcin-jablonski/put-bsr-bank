package com.example.bsrbank.rest.exceptions;

import com.example.bsrbank.logic.exceptions.InvalidAccountException;
import com.example.bsrbank.logic.exceptions.InvalidAmountException;
import com.example.bsrbank.logic.exceptions.InvalidNameException;
import com.example.bsrbank.logic.exceptions.InvalidTitleException;
import com.example.bsrbank.rest.model.InterBankTransferError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ExceptionToResponseMapper {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public void handleAccountDoesNotExists(NotFoundException e) {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAmountException.class)
    public InterBankTransferError handleInvalidTransferAmount(InvalidAmountException e) {
        return new InterBankTransferError("amount", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTitleException.class)
    public InterBankTransferError handleInvalidTransferTitle(InvalidTitleException e) {
        return new InterBankTransferError("title", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidNameException.class)
    public InterBankTransferError handleInvalidTransferName(InvalidNameException e) {
        return new InterBankTransferError("name", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAccountException.class)
    public InterBankTransferError handleInvalidSourceAccount(InvalidAccountException e) {
        return new InterBankTransferError("source_account", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public void handleInvalidCredentials(UnauthorizedException e) {

    }
}
