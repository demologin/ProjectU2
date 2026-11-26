package com.javarush.quest.config.annotation;

import com.javarush.quest.exception.ErrorMessage;

public interface ServletMetadata {

    default String[] getUrlPatterns(Class<?> type) {
        Servlet annotation = type.getAnnotation(Servlet.class);
        return process(annotation);
    }

    default String[] getUrlPatterns(Servlet annotation) {
        return process(annotation);
    }

    private String[] process(Servlet annotation) {
        String[] value = annotation.value();
        String[] urlPatterns = annotation.urlPatterns();

        if (value.length > 0 && urlPatterns.length > 0) {
            throw new IllegalStateException(ErrorMessage.INVALID_URL_PATTERNS);
        }
        return value.length > 0 ? value : urlPatterns;
    }
}
