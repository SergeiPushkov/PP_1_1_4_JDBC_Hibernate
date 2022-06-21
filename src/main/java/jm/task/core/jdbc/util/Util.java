package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/qwerty";
    private static final String USERNAME = "Dark";
    private static final String PASSWORD = "Dark113355..";


    public static Connection getConnection() {
        Connection conn = null;

        try {
        conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Неудачно");
        }
        return conn;
    }
}
