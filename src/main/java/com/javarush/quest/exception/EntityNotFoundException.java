package com.javarush.quest.exception;

import java.net.HttpURLConnection;

public class EntityNotFoundException extends AppException{

    public EntityNotFoundException(String message) {
        super(message, HttpURLConnection.HTTP_NOT_FOUND);
    }

    public static EntityNotFoundException of(Class<?> type) {
        return new EntityNotFoundException(
                String.format(ErrorMessage.ENTITY_NOT_FOUND, type.getSimpleName())
        );
    }
}
