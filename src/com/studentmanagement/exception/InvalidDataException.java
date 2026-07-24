package com.studentmanagement.exception;

/**
 * Exception thrown when validation fails.
 */
public class InvalidDataException extends AppException {
    public InvalidDataException(String message) {
        super(message);
    }
}
