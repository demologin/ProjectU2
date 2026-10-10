package com.javarush.quest.exception;

import java.net.HttpURLConnection;

import static com.javarush.quest.exception.ErrorMessage.ENTITY_NOT_FOUND;

public class EntityNotFoundException extends AppException{

    public EntityNotFoundException(String message) {
        super(message, HttpURLConnection.HTTP_NOT_FOUND);
    }

    public static EntityNotFoundException of(Class<?> type) {
        return new EntityNotFoundException(
                String.format(ENTITY_NOT_FOUND, type.getSimpleName())
        );
    }
}
