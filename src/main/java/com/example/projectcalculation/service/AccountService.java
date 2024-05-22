package com.example.projectcalculation.service;

import com.example.projectcalculation.model.AccountModel;
import com.example.projectcalculation.model.PermissionLevel;
import com.example.projectcalculation.repository.UserRepository;
import com.example.projectcalculation.utilities.PasswordHashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    UserRepository userRepository;

    public void createAccount(AccountModel newUser) {
        userRepository.createAccount(newUser);
    }

    public void updateAccount(AccountModel newUser) {
        userRepository.updateAccount(newUser);
    }

    public List<AccountModel> getAllUsers() {
        return userRepository.getAllUsers();
    }
    public List<AccountModel> getAllUserEmployee() {
        return userRepository.getAllEmployee();
    }
    public AccountModel getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public AccountModel getById(Long id) {
        return userRepository.getById(id);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }
}

