package com.example.adapter.input.controller.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class DuplicatedException extends BusinessException {

    @Serial
    private static final long serialVersionUID = 8379432021656028515L;

    public DuplicatedException(String message) {
        super(HttpStatus.CONFLICT, null, null, null, message, null);
    }
}
