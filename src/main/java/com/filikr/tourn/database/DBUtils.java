package com.filikr.tourn;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {

    public static Connection getConnection() {
        String dbURL = null;
        String dbUsername = "sa";
        String dbPassword = "";

        FileInputStream fis;
        Properties properties = new Properties();

        try {
            fis = new FileInputStream("classpath:config.properties");
            properties.load(fis);

            dbURL = properties.getProperty("db.url");
            dbUsername = properties.getProperty("db.name");
            dbPassword = properties.getProperty("db.pass");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

}
