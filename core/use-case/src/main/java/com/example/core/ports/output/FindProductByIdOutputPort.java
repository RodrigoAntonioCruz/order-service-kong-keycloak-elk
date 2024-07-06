package com.example.core.ports.output;

import com.example.core.Product;

import java.util.Optional;

public interface FindProductByIdOutputPort {
    Optional<Product> findById(String productId);
}
