package com.example.adapter.input.controller.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class GenericException extends BusinessException {

	@Serial
	private static final long serialVersionUID = 3823335406673292457L;

	public GenericException(String message) {
		super(HttpStatus.BAD_REQUEST, null, null, null, message, null);
	}
}