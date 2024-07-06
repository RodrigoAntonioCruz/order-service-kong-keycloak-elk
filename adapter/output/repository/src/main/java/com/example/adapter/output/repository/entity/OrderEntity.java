package com.example.adapter.output.repository.entity;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Document(collection = "orders")
public class OrderEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4193301264047593807L;

    @Id
    private String id;
    private String orderId;
    private String userId;
    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal total;
    private LocalDate date;
    private List<String> productIds;
    private boolean processed;
}
