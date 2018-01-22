package com.example.bsrbank.logic;

import org.springframework.stereotype.Service;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

@Service
public class BanksService {
    private Dictionary<String, String> banksAddresses;

    public static String BANK_ID = "00117270";

    BanksService() {
        banksAddresses = new Hashtable<>();
        banksAddresses.put("117271", "http://localhost:8080/");
    }

    public String getBankId(String accountNumber) {
        return accountNumber.substring(2, 10);
    }

    public String getBankUrl(String bankId) {
        String trimmedId = bankId.replaceFirst("^0+(?!$)", "");
        return banksAddresses.get(trimmedId);
    }
}
