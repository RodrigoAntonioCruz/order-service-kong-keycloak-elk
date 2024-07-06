package com.example.core;

import com.example.core.utils.Constants;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = -1923082988359455179L;
    private String id;
    private BigDecimal value;
    public Product() {

    }
    public Product(String id, BigDecimal value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void validate() {
        validateFields(id, Constants.ID_NOT_NULL);
        validateFields(String.valueOf(value), Constants.VALUE_NOT_NULL);
    }

    private void validateFields(String value, String message) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Product product = (Product) object;
        return Objects.equals(id, product.id) && Objects.equals(value, product.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", value=" + value +
                '}';
    }
}
