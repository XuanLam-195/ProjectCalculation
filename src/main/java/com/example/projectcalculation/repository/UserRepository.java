package com.example.projectcalculation.repository;

import com.example.projectcalculation.model.AccountModel;
import com.example.projectcalculation.model.PermissionLevel;
import com.example.projectcalculation.utilities.ConnectionManager;
import com.example.projectcalculation.utilities.PasswordHashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    ConnectionManager connectionManager;

    public void createAccount(AccountModel newUser){
        try {
            Connection connection = connectionManager.getConnection();
            final String CREATE_QUERY = "INSERT INTO users" +
                    "(email, password, firstname, lastname, permission_level)" +
                    " VALUES(?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(CREATE_QUERY);
            pstmt.setString(1, newUser.getEmail());
            pstmt.setString(2, PasswordHashing.doHashing(newUser.getPassword()));
            pstmt.setString(3, newUser.getFirstName());
            pstmt.setString(4, newUser.getLastName());
            pstmt.setString(5, String.valueOf(newUser.getPermissionLevel()));

            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Error: Could not add Account to database and addAccount. ");
            e.printStackTrace();
        }
    }

    public void updateAccount(AccountModel newUser){
        try {
            Connection connection = connectionManager.getConnection();
            final String CREATE_QUERY = "INSERT INTO users(firstname, lastname, permission_level)" +
                    " VALUES(?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(CREATE_QUERY);
            pstmt.setString(1, newUser.getFirstName());
            pstmt.setString(2, newUser.getLastName());
            pstmt.setString(3, String.valueOf(newUser.getPermissionLevel()));

            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("Error: Could not add Account to database and addAccount. ");
            e.printStackTrace();
        }
    }

    public List<AccountModel> getAllUsers(){
        List<AccountModel> userList = new ArrayList<>();

        try{
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);

            while(resultSet.next()){
                Long id = resultSet.getLong(1);
                String email = resultSet.getString(2);
                String password = resultSet.getString(3);
                String firstName = resultSet.getString(4);
                String lastName = resultSet.getString(5);
                PermissionLevel permissionLevel = PermissionLevel.valueOf(resultSet.getString(6));
                AccountModel accountModel = new AccountModel(id, email, password, firstName, lastName, permissionLevel);
                userList.add(accountModel);
            }
        }catch (SQLException e){
            System.out.println("Error: Could not connect to database and getAllUsers. ");
            e.printStackTrace();
        }
        return userList;
    }


    public List<AccountModel> getAllEmployee(){
        List <AccountModel> userList = new ArrayList<>();

        try{
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM users where permission_level = '" + PermissionLevel.EMPLOYEE + "'";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);

            while(resultSet.next()){
                Long id = resultSet.getLong(1);
                String email = resultSet.getString(2);
                String password = resultSet.getString(3);
                String firstName = resultSet.getString(4);
                String lastName = resultSet.getString(5);
                PermissionLevel permissionLevel = PermissionLevel.valueOf(resultSet.getString(6));
                AccountModel accountModel = new AccountModel(id, email, password, firstName, lastName,  permissionLevel);
                userList.add(accountModel);
            }
        } catch(SQLException e){
            System.out.println("Error: Could not connect to database and getAllEmployee.");
            e.printStackTrace();
        }
        return userList;
    }


    public AccountModel getByEmail(String email){
        try{
            Connection connection = connectionManager.getConnection();
            final String SQL_QUERY ="SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                PermissionLevel permissionLevel = PermissionLevel.valueOf(resultSet.getString("permission_level"));
                AccountModel accountModel = new AccountModel(id, email, password, firstName, lastName, permissionLevel);
                return accountModel;
            }
        }catch (SQLException e){
            System.out.println("Error: Could not connect to database and getAllUsers. ");
            e.printStackTrace();
        }
        return null;
    }

    public AccountModel getById(Long id){

        try{
            Connection connection = connectionManager.getConnection();
            final String SQL_QUERY ="SELECT * FROM users WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setLong(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                String email = resultSet.getString(2);
                String password = resultSet.getString(3);
                String firstName = resultSet.getString(4);
                String lastName = resultSet.getString(5);
                PermissionLevel permissionLevel = PermissionLevel.valueOf(resultSet.getString(6));
                AccountModel accountModel = new AccountModel(id, email, password, firstName, lastName, permissionLevel);
                return accountModel;
            }
        }catch (SQLException e){
            System.out.println("Error: Could not connect to database and getAllUsers. ");
            e.printStackTrace();
        }
        return null;
    }


    public void delete(Long id){

        try {
            Connection connection = connectionManager.getConnection();

            final String SQL_QUERY = "DELETE FROM users WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(SQL_QUERY);
            pstmt.setLong(1, id);
            pstmt.execute();
        }catch (SQLException e){
            System.out.println("Error: Could not connect to database and getAllUsers. ");
            e.printStackTrace();
        }
    }
}
