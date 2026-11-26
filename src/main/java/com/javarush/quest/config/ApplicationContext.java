package com.javarush.quest.config;

import com.javarush.quest.config.annotation.Component;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static com.javarush.quest.exception.ErrorMessage.*;

public class ApplicationContext {
    @Getter
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
            throw new RuntimeException(CANNOT_CREATE_BEAN + type.getName(), e);
        }
    }

    public void scan(Class<?> rootClass) {
        String basePackage = rootClass.getPackageName();
        Path rootPath = resolveRootPath(rootClass, basePackage);

        try (Stream<Path> files = Files.walk(rootPath)) {
            files.filter(path -> path.toString().endsWith(".class"))
                    .filter(Files::isRegularFile)
                    .map(rootPath::relativize)
                    .map(Path::toString)
                    .map(path -> mapPathToClass(path, basePackage))
                    .filter(this::isRegularClass)
                    .filter(this::isComponent)
                    .forEach(this::getBean);
        } catch (IOException e) {
            throw new RuntimeException(CANNOT_SCAN_PACKAGE + basePackage, e);
        }
    }

    private Path resolveRootPath(Class<?> rootClass, String basePackage) {
        String packagePath = basePackage.replace('.', '/');
        URL resource = rootClass.getClassLoader().getResource(packagePath);
        Objects.requireNonNull(resource, PACKAGE_NOT_FOUND + basePackage);

        try {
            return Path.of(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(CANNOT_SCAN_PACKAGE + basePackage, e);
        }
    }

    private Class<?> mapPathToClass(String relativePath, String basePackage) {
        String className = relativePath
                .substring(0, relativePath.length() - 6) //remove .class
                .replace(File.separatorChar, '.')
                .transform(path -> basePackage.isEmpty() ?
                        path : "%s.%s".formatted(basePackage, path));
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(CLASS_NOT_FOUND + className, e);
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
                .filter(annotationType -> {
                    String packageName = annotationType.getPackageName();
                    return !packageName.startsWith("java.lang.annotation");
                })
                .anyMatch(this::isComponent);
    }
}
