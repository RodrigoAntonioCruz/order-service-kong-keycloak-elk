package com.example.core.order;

import com.example.core.Order;
import com.example.core.ports.input.SaveOrderInputPort;
import com.example.core.ports.output.SaveOrderOutputPort;
import com.example.core.utils.Constants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static com.example.core.exception.ExceptionUtil.throwExceptionIf;

@Named
@ApplicationScoped
public class SaveOrderUseCase implements SaveOrderInputPort {
    private final SaveOrderOutputPort saveOrderOutputPort;

    @Inject
    public SaveOrderUseCase(final SaveOrderOutputPort saveOrderOutputPort) {
        this.saveOrderOutputPort = saveOrderOutputPort;
    }

    @Override
    public Order save(Order order) {
        order.validate();

        throwExceptionIf(Objects.isNull(order.getDate()) || order.getDate().isAfter(LocalDate.now()),
                new IllegalArgumentException(Constants.INVALID_DATE));

        throwExceptionIf(Objects.isNull(order.getTotal()) || order.getTotal().compareTo(BigDecimal.ZERO) <= 0,
                new IllegalArgumentException(Constants.INVALID_TOTAL_VALUE));


        return saveOrderOutputPort.save(order);
    }
}