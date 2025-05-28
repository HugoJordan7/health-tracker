package com.example.healthtracker.domain.exception;

public class InvalidPasswordException extends RuntimeException {

    public static String message = "Senha incorreta";
    public InvalidPasswordException() {
        super(message);
    }

}
