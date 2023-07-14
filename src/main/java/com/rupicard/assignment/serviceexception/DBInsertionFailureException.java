package com.rupicard.assignment.serviceexception;

public class DBInsertionFailureException extends RuntimeException {

    private final String message;

    public DBInsertionFailureException(String message) {
        super(message);
        this.message = message;
    }

    public String getExceptionMessage() {
        return this.message;
    }

}
