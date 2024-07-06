package com.example.adapter.input.controller.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class NotFoundException extends BusinessException {

	@Serial
	private static final long serialVersionUID = 3823335406673292457L;

	public NotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, null, null, null, message, null);
	}
}