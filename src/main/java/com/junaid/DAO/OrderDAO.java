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

    public boolean addOrder(int customerid, int bookid, int quantity, double totalprice, String orderstatus) {
        try {
            String sql = "INSERT INTO Order (customerid, bookid, quantity, totalprice, orderstatus) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, customerid);
            statement.setInt(2, bookid);
            statement.setInt(3, quantity);
            statement.setDouble(4, totalprice);
            statement.setString(5, orderstatus);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to add a new user: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to add a new user: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteOrder(int orderid) {
        try {
            String sql = "DELETE FROM Order WHERE orderid = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, orderid);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("A user was deleted successfully!");
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

    public boolean updateOrder(int orderid, int customerid, int bookid, int quantity, double totalprice) {
        try {
            String sql = "UPDATE Order SET customerid = ?, bookid = ?, quantity = ?, totalprice = ? WHERE orderid = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, customerid);
            statement.setInt(2, bookid);
            statement.setInt(3, quantity);
            statement.setDouble(4, totalprice);
            statement.setInt(5, orderid);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to update a user: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to update a user: " + e.getMessage());
            return false;
        }
        return true;
    }

    public Order getOrder(int orderid) {
        Order order = new Order();

        try {
            String sql = "SELECT * FROM Order WHERE orderid = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, orderid);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("orderid");
                int customerid = rs.getInt("customerid");
                int bookid = rs.getInt("bookid");
                int quantity = rs.getInt("quantity");
                Date orderdate = rs.getDate("orderdate");
                String orderstatus = rs.getString("orderstatus");
                double totalprice = rs.getDouble("totalprice");

                order = new Order(id, customerid, bookid, quantity, orderdate, orderstatus, totalprice);
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
            String sql = "SELECT * FROM Order";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("orderid");
                int customerid = rs.getInt("customerid");
                int bookid = rs.getInt("bookid");
                int quantity = rs.getInt("quantity");
                Date orderdate = rs.getDate("orderdate");
                String orderstatus = rs.getString("orderstatus");
                double totalprice = rs.getDouble("totalprice");

                Order order = new Order(id, customerid, bookid, quantity, orderdate, orderstatus, totalprice);
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
