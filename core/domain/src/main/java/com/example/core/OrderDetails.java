package com.example.core;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class OrderDetails implements Serializable {
    @Serial
    private static final long serialVersionUID = -1923082988359455179L;
    private String id;
    private String name;
    private List<Orders> orders;

    public OrderDetails() {

    }

    public OrderDetails(String id, String name, List<Orders> orders) {
        this.id = id;
        this.name = name;
        this.orders = orders;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        OrderDetails that = (OrderDetails) object;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, orders);
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", orders=" + orders +
                '}';
    }
}
