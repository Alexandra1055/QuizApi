package com.example.quizapi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnector {
    public static String url = "jdbc:mysql://db:3306/quiz";
    public static String user = "root";
    public static String password = "root";

    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }   catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
