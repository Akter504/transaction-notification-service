package ru.java.maryan.api.transactionnotificationservice.exceptions;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
