package com.example.core.user;

import com.example.core.User;
import com.example.core.ports.input.SaveUserInputPort;
import com.example.core.ports.output.SaveUserOutputPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class SaveUserUseCase implements SaveUserInputPort {

    private final SaveUserOutputPort saveUserOutputPort;

    @Inject
    public SaveUserUseCase(SaveUserOutputPort saveUserOutputPort) {
        this.saveUserOutputPort = saveUserOutputPort;
    }

    @Override
    public User save(final User user) {
        user.validate();
        return saveUserOutputPort.save(user);
    }
}
