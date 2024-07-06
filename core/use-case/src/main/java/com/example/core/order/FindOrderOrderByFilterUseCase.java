package com.example.core.order;

import com.example.core.OrderDetails;
import com.example.core.ports.input.FindOrderByFilterInputPort;
import com.example.core.ports.output.FindOrderByFilterOutputPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@ApplicationScoped
public class FindOrderOrderByFilterUseCase implements FindOrderByFilterInputPort {

    private final FindOrderByFilterOutputPort findOrderByFilterOutputPort;

    @Inject
    public FindOrderOrderByFilterUseCase(final FindOrderByFilterOutputPort findOrderByFilterOutputPort) {
        this.findOrderByFilterOutputPort = findOrderByFilterOutputPort;
    }

    @Override
    public List<OrderDetails> findByFilter(String query) {
        return findOrderByFilterOutputPort.findByFilter(query);
    }
}
