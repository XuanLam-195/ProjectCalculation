package com.example.projectcalculation.repository;


import com.example.projectcalculation.model.AccountModel;
import com.example.projectcalculation.model.PermissionLevel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:h2schema.sql")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findAllUser() {
        int exceptedResult = 3;
        int actualResult = userRepository.getAllUsers().size();
        System.out.println("Size subproject belong to project 1" + actualResult);
        assertEquals(exceptedResult, actualResult);
    }


    @Test
    void createUser() {
        AccountModel accountModel = new AccountModel(null, "email@email.com", "password", "firstName", "lastName", PermissionLevel.EMPLOYEE);
        userRepository.createAccount(accountModel);
        int exceptedResult = 4;
        int actualResult = userRepository.getAllUsers().size();
        System.out.println("Size project" + actualResult);
        assertEquals(exceptedResult, actualResult);
    }


    @Test
    void updateAccount() {
        // Forbered testdata - Opret en eksisterende konto
        AccountModel existingAccount = new AccountModel(null, "email@email.com", "password", "firstName", "lastName", PermissionLevel.EMPLOYEE);
        userRepository.createAccount(existingAccount);

        // Hent den oprettede konto for at få dens ID
        AccountModel createdAccount = userRepository.getByEmail("email@email.com");
        Long accountId = createdAccount.getId();
        assertNotNull(accountId, "Account ID should not be null after creation");

        // Opret en ny brugermodel med opdaterede oplysninger
        AccountModel updatedAccount = new AccountModel(accountId, "updated@email.com", "newpassword", "NewFirstName", "NewLastName", PermissionLevel.ADMINISTRATOR);

        // Kald updateAccount-metoden
        userRepository.updateAccount(updatedAccount);

        // Hent den opdaterede konto fra databasen
        AccountModel retrievedAccount = userRepository.getById(accountId);

        // Assertion: Sammenlign oplysninger for at sikre, at opdateringen blev udført korrekt
        assertEquals(updatedAccount.getFirstName(), retrievedAccount.getFirstName(), "Fornavn blev ikke opdateret korrekt.");
        assertEquals(updatedAccount.getLastName(), retrievedAccount.getLastName(), "Efternavn blev ikke opdateret korrekt.");
        assertEquals(updatedAccount.getPermissionLevel(), retrievedAccount.getPermissionLevel(), "Tilladelsesniveau blev ikke opdateret korrekt.");
    }
}
