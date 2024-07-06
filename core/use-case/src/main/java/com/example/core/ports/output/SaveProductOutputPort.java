package com.example.core.ports.output;

import com.example.core.Product;

public interface SaveProductOutputPort {
    Product save(final Product product);
}
