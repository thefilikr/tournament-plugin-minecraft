package com.filikr.tourn.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {

    public static Connection getConnection() {
        String dbURL = "jdbc:postgresql://localhost:5432/postgres";
        String dbUsername = "filikr";
        String dbPassword = "filikr";

        FileInputStream fis;
        Properties properties = new Properties();

//

//        dbURL = "url";
//        dbUsername = "root";
//        dbPassword = "root";
//
        Connection connection = null;
//
//        try {
//            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        try {
            // Явная регистрация драйвера
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            System.out.println("Database connection established successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }

        return connection;
    }

}
