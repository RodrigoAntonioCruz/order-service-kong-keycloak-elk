package com.example.core.ports.input;

import com.example.core.Order;

public interface SaveOrderInputPort {
    Order save(Order order);
}
