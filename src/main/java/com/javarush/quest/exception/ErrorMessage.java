package com.javarush.quest.exception;

public final class ErrorMessage {
    //Exception messages
    public static final String ANNOTATION_REQUIRED = "No @Servlet annotation for class: %s";
    public static final String CANNOT_CREATE_BEAN = "Cannot create bean for class: ";
    public static final String CANNOT_SCAN_PACKAGE = "Cannot scan package: ";
    public static final String CLASS_NOT_FOUND = "Cannot find class: ";
    public static final String ENTITY_NOT_FOUND = "Matching %s entity not found";
    public static final String INVALID_CREDENTIALS = "Invalid login and/or password";
    public static final String INVALID_URL_PATTERNS = "Cannot set both 'value' and 'urlPatterns'";
    public static final String PACKAGE_NOT_FOUND = "Cannot find package: ";
    public static final String PACKAGE_NOT_FOUND_IN_PATH = "Package %s not found in path: %s";
    public static final String STARTUP_FAILED = "Application startup failed";
    public static final String UNSUPPORTED_FILE = "Cannot find appropriate parser for: ";
    public static final String UTILITY_CLASS = "This is a utility class and cannot be instantiated";

    //Http status messages
    public static final String RESOURCE_NOT_FOUND = "Resource Not Found";
    public static final String SERVER_ERROR = "Internal Server Error";
    public static final String UNAUTHORIZED = "Unauthorized";

    private ErrorMessage() {
        throw new UnsupportedOperationException(UTILITY_CLASS);
    }
}
