package com.example.adapter.output.repository.mapper;


import com.example.adapter.output.repository.entity.OrderEntity;
import com.example.core.Order;
import com.example.core.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderOutputMapper {

    @Mapping(target = "entity.processed", ignore = true)
    Order toDomain(OrderEntity entity);

    @Mapping(target = "processed", ignore = true)
    OrderEntity toOrderEntity(Order order);

    @Mapping(target = "products", ignore = true)
    Orders toOrders(OrderEntity order);
}

