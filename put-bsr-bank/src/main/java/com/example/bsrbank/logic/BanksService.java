package com.example.bsrbank.logic;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

@Service
public class BanksService {
    private Dictionary<String, String> banksAddresses;

    public static String BANK_ID = "00117270";

    BanksService() {
        banksAddresses = new Hashtable<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("C:\\Projekty\\put-bsr-bank\\put-bsr-bank\\src\\main\\java\\com\\example\\bsrbank\\banks.csv"));
            String line = "";
            while((line = reader.readLine()) != null) {
                String[] bankData = line.split(",");
                banksAddresses.put(bankData[0], bankData[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBankId(String accountNumber) {
        return accountNumber.substring(2, 10);
    }

    public String getBankUrl(String bankId) {
        return banksAddresses.get(bankId);
    }
}
