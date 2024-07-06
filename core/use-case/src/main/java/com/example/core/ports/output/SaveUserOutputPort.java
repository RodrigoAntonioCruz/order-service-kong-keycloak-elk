package com.example.core.ports.output;

import com.example.core.User;

public interface SaveUserOutputPort {
    User save(final User user);
}
