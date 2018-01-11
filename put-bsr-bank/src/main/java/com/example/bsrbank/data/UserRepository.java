package com.example.bsrbank.data;

import com.example.bsrbank.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAll();

    User findById(int id);

    boolean existsUserById(int id);
}
