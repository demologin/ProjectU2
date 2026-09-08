package com.javarush.khmelov.lesson14.config;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public final class Winter {

    private Winter() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    private static final Map<Class<?>, Object> beans = new ConcurrentHashMap<>();

    @SneakyThrows
    public static synchronized <T> T getBean(Class<T> clazz) {
        if (!beans.containsKey(clazz)) {
            Constructor<?> constructor = clazz.getConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameters.length; i++) {
                parameters[i]=Winter.getBean(parameterTypes[i]);
            }
            beans.put(clazz, constructor.newInstance(parameters));
        }
        return (T) beans.get(clazz);
    }
}
