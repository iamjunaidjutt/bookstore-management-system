package com.junaid.GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.junaid.DAO.BookDAO;
import com.junaid.Models.Book;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddBookGUI extends JFrame {
    private JTextField isbnField;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField publisherField;
    private JTextField priceField;
    private JTextField quantityField;
    private JTable table;
    private JScrollPane scrollPane;
    BookDAO bookDAO = new BookDAO();
    private int selectedRow;
    private int selectedColumn;

    public AddBookGUI() {
        setTitle("Add Book");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel isbnLabel = new JLabel("ISBN");
        isbnLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        isbnField = new JTextField();
        isbnField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        isbnField.setMaximumSize(new Dimension(200, 40));

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        titleField = new JTextField();
        titleField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        titleField.setMaximumSize(new Dimension(200, 40));

        JLabel authorLabel = new JLabel("Author");
        authorLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        authorField = new JTextField();
        authorField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        authorField.setMaximumSize(new Dimension(200, 40));

        JLabel publisherLabel = new JLabel("Publisher");
        publisherLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        publisherField = new JTextField();
        publisherField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        publisherField.setMaximumSize(new Dimension(200, 40));

        JLabel priceLabel = new JLabel("Price");
        priceLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        priceField = new JTextField();
        priceField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        priceField.setMaximumSize(new Dimension(200, 40));

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        quantityField = new JTextField();
        quantityField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        quantityField.setMaximumSize(new Dimension(200, 40));

        JButton addButton = new JButton("Add");
        addButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addButton.setMaximumSize(new Dimension(200, 40));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        cancelButton.setMaximumSize(new Dimension(200, 40));

        panel.add(Box.createVerticalGlue());
        panel.add(isbnLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(isbnField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(titleField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(authorLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(authorField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(publisherLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(publisherField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(priceLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(priceField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(quantityLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(quantityField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(cancelButton);
        panel.add(Box.createVerticalGlue());

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panel);

        displayBooks();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText();
                String title = titleField.getText();
                String author = authorField.getText();
                String publisher = publisherField.getText();
                String price = priceField.getText();
                String quantity = quantityField.getText();

                if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || publisher.isEmpty() || price.isEmpty()
                        || quantity.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields");
                } else {
                    Book book = new Book(Integer.parseInt(isbn), title, author, publisher, Double.parseDouble(price),
                            Integer.parseInt(quantity));
                    boolean isAdded = false;
                    if (bookDAO.connect()) {
                        isAdded = bookDAO.addBook(book);
                    }
                    bookDAO.disconnect();

                    if (isAdded) {
                        JOptionPane.showMessageDialog(null, "Book added successfully");
                        isbnField.setText("");
                        titleField.setText("");
                        authorField.setText("");
                        publisherField.setText("");
                        priceField.setText("");
                        quantityField.setText("");
                        dispose();
                        new HomeGUI();
                    } else {
                        JOptionPane.showMessageDialog(null, "Something went wrong");
                    }
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomeGUI();
            }
        });
    }

    public void displayBooks() {
        String[] columnNames = { "ISBN", "Title", "Author", "Publisher", "Price", "Quantity", "Actions" };
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                selectedRow = table.getSelectedRow();
                selectedColumn = table.getSelectedColumn();
                if (selectedRow != -1 && selectedColumn == 6) {
                    int isbn = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                    if (bookDAO.connect()) {
                        boolean isDeleted = bookDAO.deleteBook(isbn);
                        bookDAO.disconnect();
                        if (isDeleted) {
                            JOptionPane.showMessageDialog(null, "Book deleted successfully");
                            displayBooks();
                        } else {
                            JOptionPane.showMessageDialog(null, "Something went wrong");
                        }
                    }
                }
            }
        });

        if (bookDAO.connect()) {
            List<Book> books = bookDAO.getAllBooks();
            for (Book book : books) {
                Object[] row = new Object[7];
                row[0] = book.getIsbn();
                row[1] = book.getTitle();
                row[2] = book.getAuthor();
                row[3] = book.getPublisher();
                row[4] = "$" + book.getPrice();
                row[5] = book.getQuantity();
                row[6] = "X";
                model.addRow(row);
            }
            bookDAO.disconnect();
        }

        add(scrollPane);

        setVisible(true);
    }
}
