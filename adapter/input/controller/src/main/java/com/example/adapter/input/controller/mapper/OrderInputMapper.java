package com.example.adapter.input.controller.mapper;

import com.example.adapter.input.controller.dto.OrderDTO;
import com.example.adapter.input.controller.dto.OrdersDTO;
import com.example.adapter.input.controller.dto.ProductDTO;
import com.example.core.OrderDetails;
import com.example.core.Orders;
import com.example.core.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderInputMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "orders", target = "orders")
    OrderDTO toOrderDTO(OrderDetails orderDetails);

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "total", target = "total")
    @Mapping(source = "date", target = "date")
    @Mapping(source = "products", target = "products")
    OrdersDTO toOrdersDTO(Orders orders);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "value", target = "value")
    ProductDTO toProductDTO(Product product);
}

