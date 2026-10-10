package com.javarush.quest.config.constant;

import static com.javarush.quest.exception.ErrorMessage.UTILITY_CLASS;

public final class Schema {

    public static final class Key {
        public static final String ID = "id";
        public static final String LOGIN = "login";
        public static final String LOGOUT = "logout";
        public static final String PASSWORD = "password";
        public static final String QUEST = "quest";
        public static final String QUESTS = "quests";
        public static final String SCENE = "scene";
        public static final String SIGNUP = "signup";
        public static final String USER = "user";
        public static final String USERS = "users";

        private Key() {
            throw new UnsupportedOperationException(UTILITY_CLASS);
        }
    }

    public static final class Url {
        public static final String ROOT = "";
        public static final String HOME = "/";
        public static final String WILDCARD = "/*";

        public static final String LOGIN = HOME + Key.LOGIN;
        public static final String LOGOUT = HOME + Key.LOGOUT;
        public static final String SIGNUP = HOME + Key.SIGNUP;
        public static final String USERS = HOME + Key.USERS;

        public static final String QUEST = HOME + Key.QUEST;
        public static final String SCENE = HOME + Key.SCENE;

        private Url() {
            throw new UnsupportedOperationException(UTILITY_CLASS);
        }
    }

    public static final class Jsp {
        public static final String JSP_FORMAT = "/WEB-INF/%s.jsp";

        public static final String ERROR = "error";
        public static final String HOME = "home";
        public static final String INDEX = "index";
        public static final String PROFILE = "profile";

        private Jsp() {
            throw new UnsupportedOperationException(UTILITY_CLASS);
        }
    }

    private Schema() {
        throw new UnsupportedOperationException(UTILITY_CLASS);
    }
}
