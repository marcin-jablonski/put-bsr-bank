package com.example.bsrbank.logic;

import com.example.bsrbank.data.UserRepository;
import com.example.bsrbank.logic.exceptions.AccountNotFoundException;
import com.example.bsrbank.model.User;
import com.example.bsrbank.rest.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//handle tasks connected to users data
@Service
public class UsersService {
    @Autowired
    private UserRepository userRepository;

    public User getUser(String username) {
        return userRepository.findByName(username);
    }

    public void authorize(String username, String password) throws AccountNotFoundException, UnauthorizedException {
        User user = userRepository.findByName(username);

        if (user == null) {
            throw new AccountNotFoundException();
        }

        if (!user.getPassword().equals(password)) {
            throw new UnauthorizedException();
        }
    }
}
