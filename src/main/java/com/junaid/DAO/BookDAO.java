package com.junaid.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.junaid.Models.Book;

public class BookDAO {
    private Connection con;

    public boolean connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/bookmanagement";
            String username = "scd_admin";
            String password = "scd13579";

            Class.forName("com.mysql.cj.jdbc.Driver");
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

    public BookDAO() {
    }

    public boolean addBook(Book book) {
        try {
            String sqlQuery = "INSERT INTO Book (isbn ,title, author, publisher, price, quantity) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setInt(1, book.getIsbn());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getPublisher());
            statement.setDouble(5, book.getPrice());
            statement.setInt(6, book.getQuantity());
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new book was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to insert a new book: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to insert a new book: " + e.getMessage());
            return false;
        }
        return true;
    }

    public double getPrice(int isbn) {
        double price = 0;
        try {
            String sqlQuery = "SELECT price FROM Book WHERE isbn = ?";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setInt(1, isbn);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                price = rs.getDouble("price");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to get a book by isbn: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to get a book by isbn: " + e.getMessage());
        }
        return price;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<Book>();
        try {
            String sqlQuery = "SELECT * FROM Book";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int isbn = rs.getInt("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                Book book = new Book(isbn, title, author, publisher, price, quantity);
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to get all books: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to get all books: " + e.getMessage());
        }
        return books;
    }

    public Book getBookByISBN(int isbn) {
        Book book = null;
        try {
            String sqlQuery = "SELECT * FROM Book WHERE isbn = ?";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setInt(1, isbn);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                book = new Book(isbn, title, author, publisher, price, quantity);
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to get a book by isbn: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to get a book by isbn: " + e.getMessage());
        }
        return book;
    }

    public boolean updateBook(Book book) {
        try {
            String sqlQuery = "UPDATE Book SET title = ?, author = ?, publisher = ?, price = ?, quantity = ? WHERE isbn = ?";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getPublisher());
            statement.setDouble(4, book.getPrice());
            statement.setInt(5, book.getQuantity());
            statement.setInt(6, book.getIsbn());
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("An existing book was updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to update a book: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to update a book: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateBookQuantity(int isbn, int quantity) {
        try {
            String sqlQuery = "UPDATE Book SET quantity = ? WHERE isbn = ?";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setInt(1, quantity);
            statement.setInt(2, isbn);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("An existing book was updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to update a book: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to update a book: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean deleteBook(int isbn) {
        try {
            String sqlQuery = "DELETE FROM Book WHERE isbn = ?";
            PreparedStatement statement = con.prepareStatement(sqlQuery);
            statement.setInt(1, isbn);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("A book was deleted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong when trying to delete a book: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong when trying to delete a book: " + e.getMessage());
            return false;
        }
        return true;
    }
}