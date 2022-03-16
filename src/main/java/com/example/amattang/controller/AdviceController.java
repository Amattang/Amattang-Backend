package com.example.amattang.controller;

import com.example.amattang.exception.BadRequestException;
import com.example.amattang.payload.reponse.ResponseUtil.DefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

import static com.example.amattang.payload.reponse.ResponseUtil.fail;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(value = {BadRequestException.class, BindException.class, NoSuchElementException.class})
    public ResponseEntity<DefaultResponse> BadRequestHandler(Exception e, BindingResult result) {
        return fail(HttpStatus.BAD_REQUEST, result.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, MissingServletRequestParameterException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<DefaultResponse> IllegalArgumentHandler(Exception e) {
        return fail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

}
