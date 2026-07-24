package com.studentmanagement.exception;

/**
 * Exception thrown when login authentication fails.
 */
public class AuthenticationException extends AppException {
    public AuthenticationException(String message) {
        super(message);
    }
}
