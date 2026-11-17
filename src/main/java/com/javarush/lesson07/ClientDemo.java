package com.javarush.lesson07;

import com.javarush.khmelov.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ClientDemo {

    public static void main(String[] args) throws SQLException {
        try (
                Connection connection = Cnn.getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SQL.SELECT_ALL_USERS);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                Role role = Role.valueOf(resultSet.getString("role"));
                System.out.println(id + " " + login + " " + password + " " + role);
            }

            System.out.println();
            resultSet = statement.executeQuery(SQL.SELECT_ALL_USERS);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                String columnType = metaData.getColumnTypeName(i);
                columnNames.add(columnName);
                System.out.printf("%6s/%-8s", columnName, columnType);
            }
            System.out.println("\n" + "-".repeat(15 * columnCount));
            while (resultSet.next()) {
                for (String columnName : columnNames) {
                    String value = resultSet.getString(columnName);
                    System.out.printf("%15s", value);
                }
                System.out.println();
            }

            int count = statement.executeUpdate(SQL.INSERT_USERS, Statement.RETURN_GENERATED_KEYS);
            System.out.println("add users: " + count);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            StringJoiner joinerId = new StringJoiner(", ");
            while (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                joinerId.add(Long.toString(id));
            }
            System.out.println(joinerId.toString());


            int deleteCount = statement.executeUpdate(SQL.DELETE_USERS);
            System.out.println("delete users: " + deleteCount);
        }

    }
}
