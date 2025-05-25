package com.example.healthtracker.domain.exception;

public class EmailAlreadyExistException extends RuntimeException {

    public static final String message = "Email já cadastrado";

    public EmailAlreadyExistException() {
        super("Email já cadastrado");
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
