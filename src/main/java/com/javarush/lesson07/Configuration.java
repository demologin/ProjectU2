package com.javarush.lesson07;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private Configuration() {
        throw new IllegalStateException("Utility class");
    }

    private static final Properties properties = new Properties();

    private static final String APPLICATION_PROPERTIES = "/application.properties";

    static {
        try {
            properties.load(Configuration.class.getResourceAsStream(APPLICATION_PROPERTIES));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        //logic env 1, prop 2
        return System.getenv()
                .getOrDefault(key,
                        properties.getProperty(key)
                );
    }
}
