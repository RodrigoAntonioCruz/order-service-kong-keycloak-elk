package com.example.core.ports.output;

import com.example.core.Order;

public interface SaveOrderOutputPort {
    Order save(final Order order);
}
