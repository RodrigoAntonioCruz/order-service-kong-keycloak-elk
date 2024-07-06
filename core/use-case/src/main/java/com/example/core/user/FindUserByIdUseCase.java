package com.example.core.user;

import com.example.core.User;
import com.example.core.exception.UserNotFoundException;
import com.example.core.ports.input.FindUserByIdInputPort;
import com.example.core.ports.output.FindUserByIdOutputPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class FindUserByIdUseCase implements FindUserByIdInputPort {

    private final FindUserByIdOutputPort findUserByIdOutputPort;

    @Inject
    public FindUserByIdUseCase(final FindUserByIdOutputPort findUserByIdOutputPort) {
        this.findUserByIdOutputPort = findUserByIdOutputPort;
    }
    @Override
    public User findById(String userId) {
        return findUserByIdOutputPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
