package com.javarush.lesson08;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementDemo {

    public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";

    public static void main(String[] args) throws SQLException {
        for (int queryId = 0; queryId <= 3; queryId++) {
            printUserById(queryId);
        }
    }

    private static void printUserById(long queryId) throws SQLException {
        Connection connection = CnnPool.get();
        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, queryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                System.out.printf("id: %d, login: %s, password: %s\n", id, login, password);
            }
        }
    }
}
