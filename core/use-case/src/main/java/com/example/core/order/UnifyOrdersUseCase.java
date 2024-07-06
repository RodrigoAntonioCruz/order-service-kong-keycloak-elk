package com.example.core.order;

import com.example.core.Order;
import com.example.core.ports.input.DeleteOrderInputPort;
import com.example.core.ports.input.UnifyOrdersInputPort;
import com.example.core.ports.output.FindOrderByFilterOutputPort;
import com.example.core.ports.output.SaveOrderOutputPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class UnifyOrdersUseCase implements UnifyOrdersInputPort {
    private final FindOrderByFilterOutputPort findOrderByFilterOutputPort;
    private final SaveOrderOutputPort saveOrderOutputPort;
    private final DeleteOrderInputPort deleteOrderInputPort;

    @Inject
    public UnifyOrdersUseCase(final FindOrderByFilterOutputPort findOrderByFilterOutputPort,
                              final SaveOrderOutputPort saveOrderOutputPort,
                              final DeleteOrderInputPort deleteOrderInputPort) {
        this.findOrderByFilterOutputPort = findOrderByFilterOutputPort;
        this.saveOrderOutputPort = saveOrderOutputPort;
        this.deleteOrderInputPort = deleteOrderInputPort;
    }

    @Override
    public void unifyAndDeleteDuplicates() {
        int skip = 0;
        int limit = 1000;

        List<Order> orders;
        do {
            orders = findOrderByFilterOutputPort.findAllPaged(skip, limit);

            if (!orders.isEmpty()) {
                Map<String, Order> unifiedOrdersMap = orders.stream()
                        .collect(Collectors.toMap(
                                Order::getOrderId,
                                order -> order,
                                (existingOrder, newOrder) -> {
                                    List<String> combinedProductIds = new ArrayList<>(existingOrder.getProductIds());
                                    combinedProductIds.addAll(newOrder.getProductIds());
                                    existingOrder.setProductIds(combinedProductIds);
                                    existingOrder.setTotal(existingOrder.getTotal().add(newOrder.getTotal()));
                                    return existingOrder;
                                }
                        ));

                List<Order> unifiedOrders = new ArrayList<>(unifiedOrdersMap.values());

                orders.forEach(order -> deleteOrderInputPort.deleteById(order.getOrderId()));
                unifiedOrders.forEach(order -> {
                    saveOrderOutputPort.save(order);
                    findOrderByFilterOutputPort.markAsProcessed(order.getOrderId());
                });
            }

            skip += limit;
        } while (!orders.isEmpty());
    }
}
