package com.example.projectcalculation.repository;


import com.example.projectcalculation.model.AccountModel;
import com.example.projectcalculation.model.PermissionLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:h2schema.sql")
public class UserRepositoryTest {

    @Autowired
   UserRepository userRepository;

    @Test
    void findAllUser(){
        int exceptedResult = 3;
        int actualResult = userRepository.getAllUsers().size();
        System.out.println("Size subproject belong to project 1" + actualResult);
        assertEquals(exceptedResult, actualResult);
    }

    @ Test
    void createUser(){
        AccountModel accountModel = new AccountModel(null, "email@email.com", "password", "firstName", "lastName", PermissionLevel.EMPLOYEE);
        userRepository.createAccount(accountModel);
        int exceptedResult = 4;
        int actualResult = userRepository.getAllUsers().size();
        System.out.println("Size project" + actualResult);
        assertEquals(exceptedResult, actualResult);

    }
}

