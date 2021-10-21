package com.bets_be.loggerInfo;

public enum LoggerMessage {
    FETCH(" : fetch data method"),
    CREATE(" : create method"),
    UPDATE(" : update method"),
    DELETE(" : delete method");

    private final String message;

    LoggerMessage(String message) {
        this.message = message;
    }

    public String getMessage(String details) {
        return details + message;
    }
}
