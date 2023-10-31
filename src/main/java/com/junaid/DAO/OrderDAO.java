package com.junaid.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.junaid.Models.Order;

public class OrderDAO {
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

    public OrderDAO() {
    }

    public boolean addOrder(Order order) {
        try {
            String sqlQuery = "INSERT INTO Orders (customerid, totalprice) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setInt(1, order.getCustomerID());
            statement.setDouble(2, order.getTotalPrice());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new order was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to insert a new order: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to insert a new order: " + e.getMessage());
            return false;
        }
        return true;
    }

    public int getOrderId(int customerId, double totalPrice) {
        try {
            String sql = "SELECT orderId FROM Orders WHERE customerId = ? AND totalPrice = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setDouble(2, totalPrice);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                System.out.println("Order ID in OrderDAO: " + rs.getInt("orderId"));
                return rs.getInt("orderId");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to get order id: " + e.getMessage());
            return -1;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to get order id: " + e.getMessage());
            return -1;
        }
        return -1;
    }

    public boolean deleteOrder(int orderid) {
        try {
            String sql = "DELETE FROM Orders WHERE orderid = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, orderid);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("An order was deleted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to delete a user: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to delete a user: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteOrder(int customerId, double totalPrice) {
        try {
            String sql = "DELETE FROM Orders WHERE customerId = ? AND totalPrice = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.setDouble(2, totalPrice);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("An order was deleted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to delete a user: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to delete a user: " + e.getMessage());
            return false;
        }
        return true;
    }

    public Order getOrder(int orderid) {
        Order order = new Order();

        try {
            String sql = "SELECT * FROM Orders WHERE orderid = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, orderid);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("orderid");
                int customerid = rs.getInt("customerid");
                Date orderdate = rs.getDate("orderdate");
                double totalprice = rs.getDouble("totalprice");

                order = new Order(id, customerid, orderdate, totalprice);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to get a user by id: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to get a user by id: " + e.getMessage());
            return null;
        }
        return order;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<Order>();

        try {
            String sql = "SELECT * FROM Orders";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("orderid");
                int customerid = rs.getInt("customerid");
                Date orderdate = rs.getDate("orderdate");
                double totalprice = rs.getDouble("totalprice");

                Order order = new Order(id, customerid, orderdate, totalprice);
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to get all users: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to get all users: " + e.getMessage());
            return null;
        }
        return orders;
    }
}
