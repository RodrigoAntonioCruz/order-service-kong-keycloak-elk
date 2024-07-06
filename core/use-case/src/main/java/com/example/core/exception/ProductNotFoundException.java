package com.example.core.exception;

import com.example.core.utils.Constants;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super(String.format(Constants.PRODUCT_NOT_FOUND, productId));
    }
}
