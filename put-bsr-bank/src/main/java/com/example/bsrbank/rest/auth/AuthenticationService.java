package com.example.bsrbank.rest.auth;

import com.example.bsrbank.rest.exceptions.UnauthorizedException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Dictionary;
import java.util.Hashtable;

@Service
public class AuthenticationService {
    private Dictionary<String, String> users;

    public AuthenticationService() {
        this.users = new Hashtable<>();
        loadCredentials();
    }

    private void loadCredentials() {
        this.users.put("admin", "admin");
    }


    public void checkCredentials(String authorizationHeader) throws UnauthorizedException {
        if (authorizationHeader == null) {
            throw new UnauthorizedException();
        }
        try {
            String decoded = new String(Base64.getDecoder().decode(authorizationHeader.replace("Basic ", "")));
            String[] credentials = decoded.split(":");
            String username = credentials[0];
            String password = credentials[1];
            String retrievedPassword = this.users.get(username);
            if (retrievedPassword == null || !retrievedPassword.equals(password)) {
                throw new UnauthorizedException();
            }
        } catch (Exception ex) {
            throw new UnauthorizedException();
        }
    }
}
