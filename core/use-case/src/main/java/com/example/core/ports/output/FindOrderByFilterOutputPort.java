package com.example.core.ports.output;

import com.example.core.Order;
import com.example.core.OrderDetails;

import java.util.List;

public interface FindOrderByFilterOutputPort {
    List<OrderDetails> findByFilter(final String query);
    List<Order> findAllPaged(int skip, int limit);
    void markAsProcessed(String orderId);
}
