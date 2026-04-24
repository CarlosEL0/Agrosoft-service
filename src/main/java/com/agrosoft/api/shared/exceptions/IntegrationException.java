package com.agrosoft.api.shared.exceptions;

public class IntegrationException extends RuntimeException {

    // Constructor con solo el mensaje
    public IntegrationException(String message) {
        super(message);
    }

    // Constructor con mensaje y la causa original
    public IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}