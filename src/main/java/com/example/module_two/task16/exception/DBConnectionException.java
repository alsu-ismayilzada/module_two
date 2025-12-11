package com.example.module_two.task16.exception;

public class DBConnectionException extends RuntimeException {

    public DBConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
