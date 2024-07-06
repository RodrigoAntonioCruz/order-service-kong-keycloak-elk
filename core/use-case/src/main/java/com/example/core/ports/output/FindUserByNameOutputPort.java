package com.example.core.ports.output;

import com.example.core.User;

import java.util.List;
import java.util.Optional;

public interface FindUserByNameOutputPort {
    List<User> findUserIdsByName(String name);
}
