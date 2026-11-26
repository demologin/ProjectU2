package com.javarush.quest.exception.impl;

import com.javarush.quest.exception.AppException;

import java.net.HttpURLConnection;

import static com.javarush.quest.exception.ErrorMessage.*;

public class EntityNotFoundException extends AppException {

    public EntityNotFoundException(String message) {
        super(message, HttpURLConnection.HTTP_NOT_FOUND, RESOURCE_NOT_FOUND);
    }

    public static EntityNotFoundException of(Class<?> type) {
        return new EntityNotFoundException(
                String.format(ENTITY_NOT_FOUND, type.getSimpleName())
        );
    }
}
