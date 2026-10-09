package com.javarush.quest.exception;

import lombok.Getter;

public abstract class AppException extends RuntimeException {
    @Getter
    private final int httpStatus;

    public AppException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
