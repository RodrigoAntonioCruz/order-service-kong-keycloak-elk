package com.example.core;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Orders implements Serializable {
    @Serial
    private static final long serialVersionUID = -1923082988359455179L;
    private String orderId;
    private BigDecimal total;
    private LocalDate date;
    private List<Product> products;

    public Orders() {

    }

    public Orders(String orderId, BigDecimal total, LocalDate date, List<Product> products) {
        this.orderId = orderId;
        this.total = total;
        this.date = date;
        this.products = products;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Orders orders = (Orders) object;
        return Objects.equals(orderId, orders.orderId) && Objects.equals(total, orders.total) && Objects.equals(date, orders.date) && Objects.equals(products, orders.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, total, date, products);
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", total=" + total +
                ", date=" + date +
                ", products=" + products +
                '}';
    }
}
