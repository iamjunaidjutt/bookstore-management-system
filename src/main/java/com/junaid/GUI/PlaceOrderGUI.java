package com.junaid.GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.junaid.DAO.BookDAO;
import com.junaid.DAO.CustomerDAO;
import com.junaid.Models.Book;
import com.junaid.Models.Customer;
import com.junaid.Models.OrderDetails;
import com.junaid.Service.OrderService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderGUI extends JFrame implements ActionListener {
    JPanel topPanel;
    JButton confirmOrderButton;
    JButton cancelButton;

    JPanel middlePanel;
    JLabel orderDetailsLabel;

    JPanel bottomPanel;
    JPanel leftPanel;
    JPanel rightPanel;

    JTable table1;
    JScrollPane scrollPane1;
    CustomerDAO customerDAO = new CustomerDAO();
    BookDAO bookDAO = new BookDAO();
    private int selectedCustomerRow = -1;
    int customerId = -1;

    private List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();

    JLabel selectCustomerLabel;

    JLabel selectBookLabel;

    JTable table3;
    int selectedODRow = -1;
    int selectedODColumn = -1;

    public PlaceOrderGUI() {
        setTitle("Place Order");
        setSize(1500, 800);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top Panel
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        confirmOrderButton = new JButton("Confirm Order");
        confirmOrderButton.setFocusable(false);
        confirmOrderButton.addActionListener(this);
        confirmOrderButton.setEnabled(false);
        cancelButton = new JButton("Cancel");
        cancelButton.setFocusable(false);
        cancelButton.addActionListener(this);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(confirmOrderButton);
        topPanel.add(Box.createHorizontalStrut(30));
        topPanel.add(cancelButton);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.setBounds(0, 0, 1500, 50);
        add(topPanel);

        // Middle Panel
        middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBounds(0, 50, 1500, 350);
        orderDetailsLabel = new JLabel("Order Details");
        orderDetailsLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        middlePanel.add(orderDetailsLabel);
        displayOrderDetails();

        add(middlePanel);

        // Bottom Panel
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.setBounds(0, 400, 1500, 400);

        // Left Panel
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        selectCustomerLabel = new JLabel("Select Customer");
        selectCustomerLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        leftPanel.add(selectCustomerLabel);

        displayCustomers();

        bottomPanel.add(leftPanel);

        // Right Panel
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        selectBookLabel = new JLabel("Select Books");
        selectBookLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        rightPanel.add(selectBookLabel);

        displayBooks();

        bottomPanel.add(rightPanel);

        add(bottomPanel);

        setVisible(true);
    }

    public void displayCustomers() {
        String[] columnNames = { "ID", "First Name", "Last Name", "Address", "Phone" };
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table1 = new JTable();
        table1.setModel(model);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table1.setFillsViewportHeight(true);
        scrollPane1 = new JScrollPane(table1);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        if (customerDAO.connect()) {
            for (Customer customer : customerDAO.getAllCustomers()) {
                model.addRow(new Object[] { customer.getId(), customer.getFirstName(), customer.getLastName(),
                        customer.getAddress(), customer.getPhone() });
            }
            customerDAO.disconnect();
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong when trying to connect to the database");
        }

        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    selectedCustomerRow = table1.getSelectedRow();
                    if (selectedCustomerRow != -1) {
                        customerId = (int) table1.getValueAt(selectedCustomerRow, 0);
                        displayBooks();
                    }
                }
            }
        });

        leftPanel.add(scrollPane1);
    }

    public void displayBooks() {
        String[] columnNames = { "ISBN", "Title", "Author", "Price", "Quantity" };
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        JTable table2 = new JTable();
        table2.setModel(model);
        table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table2.setFillsViewportHeight(true);
        JScrollPane scrollPane2 = new JScrollPane(table2);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        if (bookDAO.connect()) {
            for (Book book : bookDAO.getAllBooks()) {
                model.addRow(new Object[] { book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPrice(),
                        book.getQuantity() });
            }
            bookDAO.disconnect();
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong when trying to connect to the database");
        }

        // allow multiple rows to be selected
        table2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Add a ListSelectionListener to track selected rows
        table2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Get selected rows
                    int[] selectedRows = table2.getSelectedRows();
                    OrderDetails orderDetail = new OrderDetails();
                    int quantity = 0;

                    // Add the newly selected rows to the list
                    for (int selectedRow : selectedRows) {
                        Book book = null;
                        if (bookDAO.connect()) {
                            book = bookDAO.getBookByISBN((int) table2.getValueAt(selectedRow, 0));
                        }
                        bookDAO.disconnect();

                        String quantityy = JOptionPane.showInputDialog("Enter Quantity: ");

                        orderDetail.setIsbn(book.getIsbn());
                        quantity = Integer.parseInt(quantityy);
                        orderDetail.setQuantity(quantity);

                        if (quantity > book.getQuantity()) {
                            JOptionPane.showMessageDialog(null, "Quantity is greater than available quantity");
                            return;
                        }

                        System.out
                                .println("ISBN: " + orderDetail.getIsbn() + " Quantity: " + orderDetail.getQuantity());

                    }

                    // if orderDetails already in list then update the quantity
                    if (orderDetails.size() == 0) {
                        orderDetails.add(orderDetail);
                    } else {
                        boolean isFound = false;
                        for (OrderDetails orderDetail2 : orderDetails) {
                            if (orderDetail2.getIsbn() == orderDetail.getIsbn()) {
                                orderDetail2.setQuantity(orderDetail2.getQuantity() + quantity);
                                isFound = true;
                            }
                        }
                        if (!isFound)
                            orderDetails.add(orderDetail);
                    }

                    // Enable the confirmOrderButton
                    if (selectedCustomerRow != -1 && orderDetails.size() > 0) {
                        confirmOrderButton.setEnabled(true);
                    }
                    updateOrderDetails();
                }
                // System.out.println("--------Start--------");
                // for (OrderDetails orderDetail : orderDetails) {
                // System.out.println(orderDetail.getIsbn() + " " + orderDetail.getQuantity());
                // }
                // System.out.println("--------End--------");
            }
        });

        rightPanel.add(scrollPane2);
    }

    public void displayOrderDetails() {
        String[] columnNames = { "CustomerId", "ISBN", "Quantity", "Actions" };
        DefaultTableModel model = new DefaultTableModel();

        model.setColumnIdentifiers(columnNames);
        table3 = new JTable();
        table3.setModel(model);
        table3.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table3.setFillsViewportHeight(true);
        JScrollPane scrollPane3 = new JScrollPane(table3);
        scrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        for (OrderDetails orderDetail : orderDetails) {
            model.addRow(new Object[] { customerId, orderDetail.getIsbn(),
                    orderDetail.getQuantity(), "X" });
        }

        ListSelectionModel selectionModel = table3.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedODRow = table3.getSelectedRow();
                    selectedODColumn = table3.getSelectedColumn();
                    if (selectedODRow != -1 && selectedODColumn != -1) {
                        if (selectedODColumn == 3) {
                            orderDetails.remove(selectedODRow);
                            updateOrderDetails();
                        } else if (selectedODColumn == 2) {
                            String quantityy = JOptionPane.showInputDialog("Enter Quantity: ");

                            if (bookDAO.connect()) {
                                if (Integer.parseInt(quantityy) > bookDAO
                                        .getBookByISBN(orderDetails.get(selectedODRow).getIsbn()).getQuantity()) {
                                    JOptionPane.showMessageDialog(null, "Quantity is greater than available quantity");
                                    return;
                                }
                            }
                            bookDAO.disconnect();
                            orderDetails.get(selectedODRow).setQuantity(Integer.parseInt(quantityy));
                            updateOrderDetails();
                        }
                    }
                }
            }
        });

        middlePanel.add(scrollPane3);
    }

    public void updateOrderDetails() {
        DefaultTableModel model = (DefaultTableModel) table3.getModel();

        // Clear the existing rows in the table
        model.setRowCount(0);

        // Add the updated order details to the table
        for (OrderDetails orderDetail : orderDetails) {
            model.addRow(new Object[] { customerId, orderDetail.getIsbn(), orderDetail.getQuantity(), "X" });
        }

        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedODRow = table3.getSelectedRow();
                    selectedODColumn = table3.getSelectedColumn();
                    if (selectedODRow != -1 && selectedODColumn != -1) {
                        if (selectedODColumn == 3) {
                            orderDetails.remove(selectedODRow);
                            updateOrderDetails();
                        } else if (selectedODColumn == 2) {
                            String quantityy = JOptionPane.showInputDialog("Enter Quantity: ");
                            if (bookDAO.connect()) {
                                if (Integer.parseInt(quantityy) > bookDAO
                                        .getBookByISBN(orderDetails.get(selectedODRow).getIsbn()).getQuantity()) {
                                    JOptionPane.showMessageDialog(null, "Quantity is greater than available quantity");
                                    return;
                                }
                            }
                            bookDAO.disconnect();
                            orderDetails.get(selectedODRow).setQuantity(Integer.parseInt(quantityy));
                            updateOrderDetails();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmOrderButton) {
            JOptionPane.showMessageDialog(null, "Order Confirmed");
            OrderService orderService = new OrderService();
            if (orderService.placeOrder(customerId, orderDetails))
                JOptionPane.showMessageDialog(null, "Order Placed");
            else
                JOptionPane.showMessageDialog(null, "Something went wrong when trying to place the order");
            dispose();
            new HomeGUI();
        } else if (e.getSource() == cancelButton) {
            JOptionPane.showMessageDialog(null, "Order Cancelled");
            dispose();
            new HomeGUI();
        }
    }
}
