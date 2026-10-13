package com.javarush.quest.exception.impl;

import com.javarush.quest.exception.AppException;

import java.net.HttpURLConnection;

import static com.javarush.quest.exception.ErrorMessage.UNAUTHORIZED;

public class InvalidCredentialsException extends AppException {

    public InvalidCredentialsException(String message) {
        super(message, HttpURLConnection.HTTP_UNAUTHORIZED, UNAUTHORIZED);
    }
}
