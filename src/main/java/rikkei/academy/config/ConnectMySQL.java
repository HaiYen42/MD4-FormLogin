package rikkei.academy.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectMySQL {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/form_login";
    private static final String USER = "root";
    private static final String PASS = "Yenhanguyen42";
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("KET NOI THANH CONG");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("KET NOI THAT BAI");
            throw new RuntimeException(e);
        }
        return connection;
    }
}
