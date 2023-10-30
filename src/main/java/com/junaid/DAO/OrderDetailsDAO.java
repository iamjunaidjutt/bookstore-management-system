package com.junaid.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.junaid.Models.OrderDetails;

public class OrderDetailsDAO {
    private Connection con;

    public boolean connect() {
        try {
            String url = "jdbc:mariadb://localhost:3306/bookmanagement";
            String username = "admin";
            String password = "jun4648";

            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to connect to the database: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to connect to the database: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean disconnect() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to disconnect from the database: " + e.getMessage());
            return false;
        }
        return true;
    }

    public OrderDetailsDAO() {
    }

    public boolean deleteOrderDetails(int orderid) {
        try {
            String sql = "DELETE FROM OrderDetails WHERE orderid = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, orderid);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("A order details was deleted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to delete a order details: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to delete a order details: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean addOrderDetails(OrderDetails orderDetails) {
        try {
            String sqlQuery = "INSERT INTO OrderDetails (orderid, isbn, quantity) VALUES (?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setInt(1, orderDetails.getOrderId());
            statement.setInt(2, orderDetails.getIsbn());
            statement.setInt(3, orderDetails.getQuantity());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new order details was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to insert a new order details: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to insert a new order details: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean placeOrder(int orderID, int isbn, int quantity) {
        try {
            String sqlQuery = "INSERT INTO OrderDetails (orderid, isbn, quantity) VALUES (?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setInt(1, orderID);
            statement.setInt(2, isbn);
            statement.setInt(3, quantity);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new order details was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to insert a new order details: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to insert a new order details: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<OrderDetails> getAllOrderDetails() {
        List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        try {
            String sqlQuery = "SELECT * FROM OrderDetails";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderDetailsId(rs.getInt("orderdetailsid"));
                orderDetails.setOrderId(rs.getInt("orderid"));
                orderDetails.setIsbn(rs.getInt("isbn"));
                orderDetails.setQuantity(rs.getInt("quantity"));
                orderDetailsList.add(orderDetails);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to get all order details: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to get all order details: " + e.getMessage());
        }
        return orderDetailsList;
    }

    public List<OrderDetails> getAllOrderDetailsById(int orderid) {
        List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        try {
            String sqlQuery = "SELECT * FROM OrderDetails WHERE orderid = ?";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setInt(1, orderid);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setOrderDetailsId(rs.getInt("orderdetailsid"));
                orderDetails.setOrderId(rs.getInt("orderid"));
                orderDetails.setIsbn(rs.getInt("isbn"));
                orderDetails.setQuantity(rs.getInt("quantity"));
                orderDetailsList.add(orderDetails);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to get all order details: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to get all order details: " + e.getMessage());
        }
        return orderDetailsList;
    }
}
