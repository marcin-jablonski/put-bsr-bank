package com.example.bsrbank.logic.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT, faultStringOrReason = "Insufficient funds on source account")
public class InsufficientFundsException extends Exception {
}
