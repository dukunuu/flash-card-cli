package com.example;

import java.sql.*;

public class ConnectionApp implements AppInterface {
    private String URL = "jdbc:mysql://localhost/";
    private String DBName;
    private final String USER = "root";
    private final String PASSWORD = "Nekoduku1";
    private Connection connection;
    private boolean success;

    public ConnectionApp(String DBName) {
        this.DBName = DBName;
        this.URL = this.URL + this.DBName;
    }

    public void launch() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            success = true;
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
            success = false;
        }
    }

    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to close the database connection: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public boolean connectionSuccess() {
        return success;
    }

    @Override
    public String printDescription() {
        return "Successfully connected to DataBase\n\n-----------Welcome to FlashCardCLI-----------";
    }
}
