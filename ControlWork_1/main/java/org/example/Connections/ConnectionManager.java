package org.example.Connections;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConnectionManager {
    private final static String USERNAME_KEY = "db.username";
    private final static String PASSWORD_KEY = "db.password";
    private final static String URL_KEY = "db.url";

    private ConnectionManager() {}
    static {
        try {
            Class.forName("java.sql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static Connection open() {
        try {
            return DriverManager.getConnection(
                    UtilProperties.get2(URL_KEY),
                    UtilProperties.get2(USERNAME_KEY),
                    UtilProperties.get2(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Connection connection = ConnectionManager.open();
        try {
            System.out.println(connection.getTransactionIsolation());
            String sql = """
                    CREATE TABLE IF NOT EXISTS Testing(
                    id SERIAL PRIMARY KEY,
                    data TEXT NOT NULL
                    );
                    """;
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

