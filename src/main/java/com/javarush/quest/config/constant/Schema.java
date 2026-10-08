package com.javarush.quest.config.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Schema {

    @UtilityClass
    public class Key {
        public final String ID = "id";
        public final String LOGIN = "login";
        public final String LOGOUT = "logout";
        public final String PASSWORD = "password";
        public final String QUEST = "quest";
        public final String QUESTS = "quests";
        public final String SCENE = "scene";
        public final String SIGNUP = "signup";
        public final String USER = "user";
        public final String USERS = "users";

    }

    @UtilityClass
    public class Url {
        public final String ROOT = "";
        public final String HOME = "/";

        public final String LOGIN = HOME + Key.LOGIN;
        public final String LOGOUT = HOME + Key.LOGOUT;
        public final String SIGNUP = HOME + Key.SIGNUP;
        public final String USERS = HOME + Key.USERS;

        public final String QUEST = HOME + Key.QUEST;
        public final String SCENE = HOME + Key.SCENE;

    }

    @UtilityClass
    public class Jsp {
        public final String HOME = "home";
        public final String PROFILE = "profile";

    }
}
