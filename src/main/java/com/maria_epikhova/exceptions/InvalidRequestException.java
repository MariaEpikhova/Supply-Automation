package com.maria_epikhova.exceptions;

public class InvalidRequestException extends ServerException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
