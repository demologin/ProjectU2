package com.javarush.quest.exception;

import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {
    private final int httpStatus;
    private final String errorMessage;

    public AppException(int httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public AppException(String message,
                        int httpStatus, String errorMessage) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public AppException(Throwable cause,
                        int httpStatus, String errorMessage) {
        super(cause);
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    public AppException(String message, Throwable cause,
                        int httpStatus, String errorMessage) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
