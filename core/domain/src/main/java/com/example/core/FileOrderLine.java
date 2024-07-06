package com.example.core;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class FileOrderLine implements Serializable {
    @Serial
    private static final long serialVersionUID = -1923082988359455179L;
    private String orderId;
    private String userId;
    private String userName;
    private String prodId;
    private BigDecimal value;
    private LocalDate date;

    public FileOrderLine(String orderId, String userId, String userName, String prodId, BigDecimal value, LocalDate date) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.prodId = prodId;
        this.value = value;
        this.date = date;
    }

    public FileOrderLine() {
    }

    public static FileOrderLine toFileOrderLine(String line) {
        FileOrderLine fileOrderLine = new FileOrderLine();
        fileOrderLine.setUserId(line.substring(0, 10).trim());
        fileOrderLine.setUserName(line.substring(10, 55).trim());
        fileOrderLine.setOrderId(line.substring(55, 65).trim());
        fileOrderLine.setProdId(line.substring(65, 75).trim());
        fileOrderLine.setValue(new BigDecimal(line.substring(75, 87).trim()));
        fileOrderLine.setDate(LocalDate.parse(line.substring(87, 95).trim(), DateTimeFormatter.ofPattern("yyyyMMdd")));
        return fileOrderLine;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        FileOrderLine that = (FileOrderLine) object;
        return Objects.equals(orderId, that.orderId) && Objects.equals(userId, that.userId) && Objects.equals(userName, that.userName) && Objects.equals(prodId, that.prodId) && Objects.equals(value, that.value) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, userName, prodId, value, date);
    }

    @Override
    public String toString() {
        return "FileOrderLine{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", prodId='" + prodId + '\'' +
                ", value=" + value +
                ", date=" + date +
                '}';
    }
}
