package com.example.core.ports.input;

import com.example.core.Product;

public interface FindProductByIdInputPort {
    Product findById(String productId);
}
