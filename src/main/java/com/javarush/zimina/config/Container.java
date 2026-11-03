package com.javarush.zimina.config;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Container {

    private static final Map<Class<?>, Object> components = new ConcurrentHashMap<>();

    private Container() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T find(Class<T> aClass) {
        Object component = components.get(aClass);
        if (component == null) {
            component = create(aClass);
            components.put(aClass, component);
        }
        return (T) component;
    }

    private static Object create(Class<?> aClass) {
        try {
            Constructor<?> constructor = aClass.getConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                parameters[i] = find(parameterTypes[i]);
            }
            return constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать " + aClass.getName(), e);
        }
    }
}