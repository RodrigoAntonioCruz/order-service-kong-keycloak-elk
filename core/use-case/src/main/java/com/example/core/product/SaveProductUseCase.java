package com.example.core.product;

import com.example.core.Product;
import com.example.core.ports.input.SaveProductInputPort;
import com.example.core.ports.output.SaveProductOutputPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class SaveProductUseCase implements SaveProductInputPort {

    private final SaveProductOutputPort saveProductOutputPort;

    @Inject
    public SaveProductUseCase(final SaveProductOutputPort saveProductOutputPort) {
        this.saveProductOutputPort = saveProductOutputPort;
    }

    @Override
    public Product save(Product product) {
        product.validate();
        return saveProductOutputPort.save(product);
    }
}
