package com.studentmanagement.exception;

/**
 * Exception thrown when trying to add a record with a duplicate primary key ID.
 */
public class DuplicateEntityException extends AppException {
    public DuplicateEntityException(String message) {
        super(message);
    }
}
