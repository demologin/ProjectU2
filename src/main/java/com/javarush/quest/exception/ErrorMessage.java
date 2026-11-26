package com.javarush.quest.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessage {
    //Exception messages
    public final String ANNOTATION_REQUIRED       = "No @Servlet annotation for class: %s";
    public final String CANNOT_CREATE_BEAN        = "Cannot create bean for class: ";
    public final String CANNOT_SCAN_PACKAGE       = "Cannot scan package: ";
    public final String CLASS_NOT_FOUND           = "Class not found: ";
    public final String ENTITY_NOT_FOUND          = "Matching %s entity not found";
    public final String INVALID_CREDENTIALS       = "Invalid login and/or password";
    public final String INVALID_URL_PATTERNS      = "Cannot set both 'value' and 'urlPatterns'";
    public final String PACKAGE_NOT_FOUND         = "Package not found: ";
    public final String STARTUP_FAILED            = "Application startup failed";
    public final String UNSUPPORTED_FILE          = "Cannot find appropriate parser for: ";

    //Http status messages
    public final String RESOURCE_NOT_FOUND = "Resource Not Found";
    public final String SERVER_ERROR       = "Internal Server Error";
    public final String UNAUTHORIZED       = "Unauthorized";
}
