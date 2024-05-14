package com.example.projectcalculation.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionManager {
    private static Connection connection;

    @Value("${spring.datasource.url}")
    String db_url;
    @Value("${spring.datasource.username}")
    String db_username;
    @Value("${spring.datasource.password}")
    String db_password;

    public Connection getConnection() {
        if (connection == null){
            try{
                connection = DriverManager.getConnection(db_url, db_username, db_password);
            }catch (SQLException e){
                System.out.println("Could not connect to database");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
