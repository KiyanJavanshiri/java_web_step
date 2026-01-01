package tinder.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/YOUR_DB_NAME";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "YOUR_DB_PASSWORD";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException ex) {
            System.out.println("Fail on connecting to DB: " + ex.getMessage());
            return null;
        }
    }
}
