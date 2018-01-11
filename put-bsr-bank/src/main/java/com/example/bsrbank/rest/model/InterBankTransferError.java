package com.example.bsrbank.rest.model;

public class InterBankTransferError {

    public InterBankTransferError(String error_field, String error) {
        this.error_field = error_field;
        this.error = error;
    }

    public InterBankTransferError() {
    }

    private String error_field;

    private String error;

    public String getError_field() {
        return error_field;
    }

    public void setError_field(String error_field) {
        this.error_field = error_field;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
