package com.example.core;

import com.example.core.utils.Constants;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = -5699704305117126904L;
    private String orderId;
    private String userId;
    private BigDecimal total;
    private LocalDate date;
    private List<String> productIds;

    public Order() {

    }
    public Order(String orderId, String userId, BigDecimal total, LocalDate date, List<String> productIds) {
        this.orderId = orderId;
        this.userId = userId;
        this.total = total;
        this.date = date;
        this.productIds = productIds;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public void validate() {
        validateFields(String.valueOf(orderId), Constants.ORDER_ID_NOT_NULL);
        validateFields(String.valueOf(userId), Constants.USER_ID_NOT_NULL);
        validateFields(String.valueOf(total), Constants.TOTAL_VALUE_NOT_NULL);
        validateFields(String.valueOf(date), Constants.DATE_NOT_NULL);
        validateFields(String.valueOf(date), Constants.DATE_NOT_NULL);
        validateFields(String.valueOf(productIds), Constants.PRODUCT_IDS_NOT_NULL);
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
        Order order = (Order) object;
        return Objects.equals(orderId, order.orderId) && Objects.equals(userId, order.userId) && Objects.equals(total, order.total) && Objects.equals(date, order.date) && Objects.equals(productIds, order.productIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, total, date, productIds);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", total=" + total +
                ", date=" + date +
                ", productIds=" + productIds +
                '}';
    }
}
