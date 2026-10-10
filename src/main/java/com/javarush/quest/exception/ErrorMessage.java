package com.javarush.quest.exception;

public final class ErrorMessage {
    public static final String ANNOTATION_REQUIRED = "No @Servlet annotation for class: %s";
    public static final String CANNOT_CREATE_BEAN = "Cannot create bean for class: ";
    public static final String CANNOT_SCAN_PACKAGE = "Cannot scan package: ";
    public static final String CLASS_NOT_FOUND = "Cannot find class: ";
    public static final String ENTITY_NOT_FOUND = "Matching %s entity not found";
    public static final String INVALID_URL_PATTERNS = "Cannot set both 'value' and 'urlPatterns'";
    public static final String PACKAGE_NOT_FOUND = "Cannot find package: ";
    public static final String PACKAGE_NOT_FOUND_IN_PATH = "Package %s not found in path: %s";
    public static final String UNSUPPORTED_FILE = "Cannot find appropriate parser for: ";
    public static final String UTILITY_CLASS = "This is a utility class and cannot be instantiated";

    private ErrorMessage() {
        throw new UnsupportedOperationException(UTILITY_CLASS);
    }
}
