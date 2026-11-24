package com.javarush.sharifullina.config;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

public class Winter {

    public static ConcurrentHashMap<Class<?>, Object> beans = new ConcurrentHashMap<>();


    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> T getBean(Class<T> aClass) {
        Object component = beans.get(aClass);
        if (component == null) {
            Constructor<?> constructor = aClass.getConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameters.length; i++) {
                parameters[i] = Winter.getBean(parameterTypes[i]);
            }
            Object newInstance = constructor.newInstance(parameters);
            beans.put(aClass, newInstance);
        }
        return (T) beans.get(aClass);
    }
}
