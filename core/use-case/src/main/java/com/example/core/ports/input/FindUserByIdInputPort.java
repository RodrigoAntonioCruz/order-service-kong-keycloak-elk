package com.example.core.ports.input;

import com.example.core.User;

public interface FindUserByIdInputPort {
    User findById(String userId);
}
