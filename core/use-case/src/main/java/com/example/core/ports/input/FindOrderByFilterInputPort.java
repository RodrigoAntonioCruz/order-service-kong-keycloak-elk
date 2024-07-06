package com.example.core.ports.input;

import com.example.core.OrderDetails;

import java.util.List;

public interface FindOrderByFilterInputPort {
    List<OrderDetails> findByFilter(String query);

}
