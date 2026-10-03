package com.javarush.quest.config;

import com.javarush.quest.config.annotation.Component;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Getter
public class ApplicationContext {
    private final Map<Class<?>, Object> beans = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type) {
        if (beans.containsKey(type)) return (T) beans.get(type);

        Constructor<?> constructor = type.getConstructors()[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = Arrays.stream(parameterTypes).map(this::getBean).toArray();

        try {
            T bean = (T) constructor.newInstance(parameters);
            beans.put(type, bean);
            return bean;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create bean for class " + type.getName(), e);
        }
    }

    public void scan(String basePackage) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(basePackage.replace('.', '/'));

        if (resource == null) {
            throw new RuntimeException(basePackage + " not found");
        }

        try (Stream<Path> walk = Files.walk(Paths.get(resource.toURI()))) {
            walk.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".class"))
                    .map(path -> mapPathToClass(path, basePackage))
                    .filter(this::isRegularClass)
                    .filter(this::isComponent)
                    .forEach(this::getBean);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> mapPathToClass(Path filePath, String basePackage) {
        String pathWithDots = filePath.toString()
                .replace(filePath.getFileSystem().getSeparator(), ".")
                .replace(".class", "");

        int beginIndex = pathWithDots.indexOf(basePackage);
        String className = pathWithDots.substring(beginIndex);

        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isRegularClass(Class<?> type) {
        return !type.isAnnotation() && !type.isInterface()
                && !Modifier.isAbstract(type.getModifiers());
    }

    private boolean isComponent(Class<?> type) {
        if (type.isAnnotationPresent(Component.class)) return true;

        return Arrays.stream(type.getAnnotations())
                .map(Annotation::annotationType)
                .filter(annotationType ->
                        !annotationType.getPackageName().startsWith("java.lang.annotation"))
                .anyMatch(this::isComponent);
    }
}
