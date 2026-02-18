package com.alertsystem.exception;

public class AlertAlreadyExistsException extends RuntimeException {
    public AlertAlreadyExistsException(String message) {
        super(message);
    }
}

