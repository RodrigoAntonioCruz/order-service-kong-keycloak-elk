package com.example.adapter.input.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {
    private Long orderId;
    private BigDecimal total;
    private LocalDate date;
    private List<ProductDTO> products;
}
