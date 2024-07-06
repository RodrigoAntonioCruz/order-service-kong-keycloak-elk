package com.example.core.order;


import com.example.core.ports.input.DeleteOrderInputPort;
import com.example.core.ports.output.DeleteOrderOutputPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class DeleteOrderUseCase implements DeleteOrderInputPort {
    private final DeleteOrderOutputPort deleteOrderOutputPort;

    @Inject
    public DeleteOrderUseCase(DeleteOrderOutputPort deleteOrderOutputPort) {
        this.deleteOrderOutputPort = deleteOrderOutputPort;
    }


    @Override
    public void deleteById(String orderId) {
        deleteOrderOutputPort.deleteById(orderId);
    }
}
