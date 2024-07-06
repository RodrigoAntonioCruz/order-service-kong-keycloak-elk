package com.example.adapter.input.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"user_id", "name", "orders"})
public class OrderDTO {
    @JsonProperty("user_id")
    private Long id;
    private String name;
    private List<OrdersDTO> orders;
}
