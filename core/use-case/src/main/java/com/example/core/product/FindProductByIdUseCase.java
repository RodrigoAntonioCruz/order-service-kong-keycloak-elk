package com.example.core.product;

import com.example.core.Product;
import com.example.core.exception.ProductNotFoundException;
import com.example.core.ports.input.FindProductByIdInputPort;
import com.example.core.ports.output.FindProductByIdOutputPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class FindProductByIdUseCase implements FindProductByIdInputPort {

    private final FindProductByIdOutputPort findProductByIdOutputPort;

    @Inject
    public FindProductByIdUseCase(final FindProductByIdOutputPort findProductByIdOutputPort) {
        this.findProductByIdOutputPort = findProductByIdOutputPort;
    }
    @Override
    public Product findById(String productId) {
        return findProductByIdOutputPort.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}
