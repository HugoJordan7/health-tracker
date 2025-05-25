package com.example.healthtracker.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public static final String message = "Usuário não encontrado";

    public UserNotFoundException() {
        super(message);
    }
    public UserNotFoundException(String message) {
        super(message);
    }
}
