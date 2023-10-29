package com.junaid.Models;

import java.util.Date;

public class Order {
    private int orderID;
    private int customerID;
    private int bookID;
    private int quantity;
    private Date orderDate;
    private String orderStatus;
    private double totalPrice;

    public Order() {
    }

    public Order(int orderID, int customerID, int bookID, int quantity, Date orderDate, String orderStatus,
            double totalPrice) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.bookID = bookID;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getBookID() {
        return bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
