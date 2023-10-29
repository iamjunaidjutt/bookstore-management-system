package com.junaid.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.junaid.Models.Customer;

public class CustomerDAO {
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

    public CustomerDAO() {
    }

    public boolean addCustomer(Customer customer) {
        try {
            String sql = "INSERT INTO Customer (firstname, lastname, address, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getPhone());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to insert a new user: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to insert a new user: " + e.getMessage());
            return false;
        }
        return true;
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM Customer";
        List<Customer> customers = new ArrayList<Customer>();

        try {
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                Customer customer = new Customer(id, firstName, lastName, address, phone);
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to get all customers: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to get all customers: " + e.getMessage());
        }
        return customers;
    }

    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM Customer WHERE id = ?";
        Customer customer = new Customer();

        try {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, customerId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String address = rs.getString("address");
                String phone = rs.getString("phone");

                customer = new Customer(id, firstName, lastName, address, phone);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to get a customer by id: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to get a customer by id: " + e.getMessage());
            return null;
        }
        return customer;
    }

    public boolean updateCustomer(Customer customer) {
        try {
            String sql = "UPDATE Customer SET firstname = ?, lastname = ?, address = ?, phone = ? WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, customer.getFirstName());
            statement.setString(2, customer.getLastName());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getPhone());
            statement.setInt(5, customer.getId());
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

    public boolean deleteCustomer(int customerId) {
        try {
            String sql = "DELETE FROM Customer WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, customerId);
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
}
