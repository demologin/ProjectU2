package com.javarush.quest.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessage {
    public final String NOT_FOUND = "Matching %s not found";


    public static final String UNSUPPORTED_FILE = "Cannot find appropriate parser for: ";
}
