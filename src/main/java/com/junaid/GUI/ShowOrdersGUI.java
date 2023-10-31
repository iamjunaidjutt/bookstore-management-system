package com.junaid.GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.junaid.DAO.OrderDAO;
import com.junaid.DAO.OrderDetailsDAO;
import com.junaid.Models.Customer;
import com.junaid.Models.Order;
import com.junaid.Models.OrderDetails;
import com.junaid.Service.OrderService;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShowOrdersGUI extends JFrame {
    JLabel ordersLabel;
    JTable ordersTable;
    JScrollPane oJScrollPane;
    OrderDAO orderDAO = new OrderDAO();
    List<Order> orders = new ArrayList<>();

    JLabel orderDetailsLabel;
    JTable orderDetailsTable;
    JScrollPane odJScrollPane;
    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
    List<OrderDetails> orderDetails = new ArrayList<>();

    JPanel topPanel;
    JButton homeButton;

    JPanel bottomPanel;

    private int selectedOrderID = -1;

    public ShowOrdersGUI() {
        super("Show Orders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 800);
        setLayout(null);

        // Add these to top panel
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBounds(0, 0, 1500, 50);
        homeButton = new JButton("Home");
        homeButton.setFocusable(false);
        homeButton.addActionListener(e -> {
            HomeGUI homeGUI = new HomeGUI();
            homeGUI.setVisible(true);
            dispose();
        });
        topPanel.add(homeButton);
        add(topPanel);

        // Add these to bottom panel
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.setBounds(0, 50, 1500, 800);
        displayOrders();
        displayOrderDetails();
        add(bottomPanel);
        setVisible(true);
    }

    public void displayOrders() {
        String[] columnNames = { "Order ID", "Customer ID", "Date", "Total Price", "Actions" };
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        ordersTable = new JTable(model);
        ordersTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ordersTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(ordersTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        if (orderDAO.connect()) {
            orders = orderDAO.getAllOrders();
            orderDAO.disconnect();
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong when trying to connect to the database");
        }

        for (Order order : orders) {
            model.addRow(new Object[] { order.getOrderID(), order.getCustomerID(), order.getOrderDate(),
                    order.getTotalPrice(), "X" });
        }

        ListSelectionModel selectionModel = ordersTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    int row = ordersTable.getSelectedRow();
                    int col = ordersTable.getSelectedColumn();
                    if (row >= 0) {
                        selectedOrderID = (int) ordersTable.getValueAt(row, 0);
                        System.out.println("Selected order ID: " + selectedOrderID);
                        updateDisplayOrderDetails();
                        if (col == 4) {
                            OrderService orderService = new OrderService();
                            orderService.deleteOrder(selectedOrderID);
                            updateDisplayOrders();
                            updateDisplayOrderDetails();
                        }
                    }
                }
            }
        });

        // Add the orders components to a panel
        JPanel ordersPanel = new JPanel();
        ordersPanel.setLayout(new BoxLayout(ordersPanel, BoxLayout.Y_AXIS));
        ordersPanel.add(scrollPane);

        // Add the orders panel to the main GUI
        bottomPanel.add(ordersPanel);
    }

    public void displayOrderDetails() {
        String[] columnNames = { "OrderDetails ID", "Order ID", "ISBN", "Quantity" };
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        orderDetailsTable = new JTable(model);
        orderDetailsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        orderDetailsTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(orderDetailsTable);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        if (orderDetailsDAO.connect()) {
            orderDetails = orderDetailsDAO.getAllOrderDetails();
            orderDetailsDAO.disconnect();
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong when trying to connect to the database");
        }

        for (OrderDetails orderDetail : orderDetails) {
            model.addRow(new Object[] { orderDetail.getOrderDetailsId(), orderDetail.getOrderId(),
                    orderDetail.getIsbn(), orderDetail.getQuantity() });
        }

        // Add the order details components to a panel
        JPanel orderDetailsPanel = new JPanel();
        orderDetailsPanel.setLayout(new BoxLayout(orderDetailsPanel, BoxLayout.Y_AXIS));
        orderDetailsPanel.add(scrollPane);

        // Add the order details panel to the main GUI
        bottomPanel.add(orderDetailsPanel);
    }

    public void updateDisplayOrders() {
        DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();

        // Clear the existing rows in the table
        model.setRowCount(0);

        // Add the updated orders to the table
        if (orderDAO.connect()) {
            orders = orderDAO.getAllOrders();
            orderDAO.disconnect();
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong when trying to connect to the database");
        }

        for (Order order : orders) {
            model.addRow(new Object[] { order.getOrderID(), order.getCustomerID(), order.getOrderDate(),
                    order.getTotalPrice(), "X" });
        }
    }

    public void updateDisplayOrderDetails() {
        DefaultTableModel model = (DefaultTableModel) orderDetailsTable.getModel();

        // Clear the existing rows in the table
        model.setRowCount(0);

        // Add the updated order details to the table
        if (orderDetailsDAO.connect()) {
            orderDetails = orderDetailsDAO.getAllOrderDetailsById(selectedOrderID);
            orderDetailsDAO.disconnect();
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong when trying to connect to the database");
        }

        for (OrderDetails orderDetail : orderDetails) {
            model.addRow(new Object[] { orderDetail.getOrderDetailsId(), orderDetail.getOrderId(),
                    orderDetail.getIsbn(), orderDetail.getQuantity() });
        }
    }

}
