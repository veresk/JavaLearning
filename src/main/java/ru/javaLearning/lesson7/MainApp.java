package ru.javaLearning.lesson7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MainApp {
    private static Connection connection;
    private static Statement stmt;

    public static void main(String[] args) {
        try {
            connect();

            var catDao = new CatDao(stmt);

            catDao.createTable();

            catDao.save(new Cat("Barsik"));
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:d:\\work\\java\\sqlite\\db\\ormlite.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
