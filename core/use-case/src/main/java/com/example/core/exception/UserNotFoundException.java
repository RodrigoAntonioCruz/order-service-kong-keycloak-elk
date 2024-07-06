package com.example.core.exception;

import com.example.core.utils.Constants;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super(String.format(Constants.USER_NOT_FOUND, userId));
    }
}
