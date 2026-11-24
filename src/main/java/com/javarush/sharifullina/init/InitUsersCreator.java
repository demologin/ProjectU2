package com.javarush.sharifullina.init;

import com.javarush.sharifullina.entity.Role;
import com.javarush.sharifullina.entity.User;
import com.javarush.sharifullina.service.UserService;

public class InitUsersCreator {

    private final UserService userService;

    public InitUsersCreator(UserService userService) {
        this.userService = userService;
    }

    public void createUsers() {
        userService.createUser(User.builder()
                .login("Alisa")
                .password("qwerty")
                .role(Role.USER)
                .score(15)
                .build());
        userService.createUser(User.builder()
                .login("Bob")
                .password("")
                .role(Role.GUEST)
                .score(5)
                .build());
        userService.createUser(User.builder()
                .login("Carl")
                .password("admin")
                .role(Role.ADMIN)
                .score(100)
                .build());
        userService.createUser(User.builder()
                .login("Khmelov")
                .password("admin")
                .role(Role.ADMIN)
                .score(3)
                .build());
    }
}
