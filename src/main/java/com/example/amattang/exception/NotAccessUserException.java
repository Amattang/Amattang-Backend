package com.example.amattang.exception;

public class NotAccessUserException extends RuntimeException{

    private String message;

    public NotAccessUserException(String message) {
        super(message);
        this.message = message;
    }
}
