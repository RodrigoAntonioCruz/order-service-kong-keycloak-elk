package com.example.core.ports.output;

import com.example.core.User;

import java.util.Optional;

public interface FindUserByIdOutputPort {
    Optional<User> findById(String userId);
}
