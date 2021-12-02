package com.maria_epikhova.exceptions;

public class ServerException extends Exception {
    private final String message;

    public ServerException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
