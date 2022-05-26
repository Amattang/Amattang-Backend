package com.example.amattang.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAccessUserException extends RuntimeException{

    private String message;

    public NotAccessUserException(String message) {
        super(message);
        this.message = message;
    }
}
