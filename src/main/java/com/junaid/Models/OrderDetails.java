package com.junaid.Models;

public class OrderDetails {
    private int orderDetailsId;
    private int orderId;
    private int isbn;
    private int quantity;

    public OrderDetails() {
    }

    public OrderDetails(int orderDetailsId, int orderId, int isbn, int quantity) {
        this.orderDetailsId = orderDetailsId;
        this.orderId = orderId;
        this.isbn = isbn;
        this.quantity = quantity;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getIsbn() {
        return isbn;
    }

    public int getQuantity() {
        return quantity;
    }
}