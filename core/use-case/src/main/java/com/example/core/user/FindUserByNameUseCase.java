package com.example.core.user;

import com.example.core.User;
import com.example.core.ports.input.FindUserByNameInputPort;
import com.example.core.ports.output.FindUserByNameOutputPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@ApplicationScoped
public class FindUserByNameUseCase implements FindUserByNameInputPort {

    private final FindUserByNameOutputPort findUserByNameOutputPort;

    @Inject
    public FindUserByNameUseCase(FindUserByNameOutputPort findUserByNameOutputPort) {
        this.findUserByNameOutputPort = findUserByNameOutputPort;
    }

    @Override
    public List<User> findUserIdsByName(String name) {
        return findUserByNameOutputPort.findUserIdsByName(name);
    }
}
