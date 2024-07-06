package com.example.core.ports.input;

import com.example.core.User;

import java.util.List;

public interface FindUserByNameInputPort {
    List<User> findUserIdsByName(String name);
}
