package com.javarush.lesson07;

public interface SQL {
    String SELECT_ALL_USERS = "SELECT id, login, password, role FROM users";

    String INSERT_USERS = """
            INSERT INTO users (login, password, role)
            VALUES ('Carl2', 'admin', 'ADMIN'),
                   ('Alisa2', 'qwerty', 'USER'),
                   ('Bob2', '123', 'GUEST');
            
            """;

    String DELETE_USERS = "DELETE FROM users where id >3";
}
