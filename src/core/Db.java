package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static Db instance = null;
    private Connection connection = null;
    private final String DB_URL = "jdbc:mysql://localhost:3306/rentacar";
    private final String DB_USERNAME = "root";
    private final String DB_PASS = "123Serhat";


    private Db() {
        try {
            this.connection = DriverManager.getConnection(
                    this.DB_URL,
                    this.DB_USERNAME,
                    this.DB_PASS);
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Connection getInstance()  {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Db();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return instance.getConnection();
    }
}

