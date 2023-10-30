package com.junaid.GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.junaid.DAO.CustomerDAO;
import com.junaid.Models.Customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCustomerGUI extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTable table;
    private JScrollPane scrollPane;
    CustomerDAO customerDAO = new CustomerDAO();
    private int selectedRow;
    private int selectedColumn;

    public AddCustomerGUI() {
        setTitle("Add Customer");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        firstNameField = new JTextField();
        firstNameField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        firstNameField.setMaximumSize(new Dimension(200, 40));

        JLabel lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        lastNameField = new JTextField();
        lastNameField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        lastNameField.setMaximumSize(new Dimension(200, 40));

        JLabel addressLabel = new JLabel("Address");
        addressLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addressField = new JTextField();
        addressField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addressField.setMaximumSize(new Dimension(200, 40));

        JLabel phoneLabel = new JLabel("Phone");
        phoneLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        phoneField = new JTextField();
        phoneField.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        phoneField.setMaximumSize(new Dimension(200, 40));

        JButton addButton = new JButton("Add");
        addButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addButton.setMaximumSize(new Dimension(200, 40));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        cancelButton.setMaximumSize(new Dimension(200, 40));

        panel.add(Box.createVerticalGlue());
        panel.add(firstNameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(firstNameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(lastNameLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(lastNameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addressLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(addressField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(phoneLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(phoneField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(cancelButton);
        panel.add(Box.createVerticalGlue());

        add(panel);

        displayCustomers();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String address = addressField.getText();
                String phone = phoneField.getText();

                if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields");
                } else {
                    Customer customer = new Customer(firstName, lastName, address, phone);
                    if (customerDAO.connect()) {
                        customerDAO.addCustomer(customer);
                        customerDAO.disconnect();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Something went wrong when trying to connect to the database");
                    }
                    JOptionPane.showMessageDialog(null, "Customer added successfully");
                    firstNameField.setText("");
                    lastNameField.setText("");
                    addressField.setText("");
                    phoneField.setText("");
                }
                dispose();
                new HomeGUI();
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

    public void displayCustomers() {
        String[] columnNames = { "ID", "First Name", "Last Name", "Address", "Phone", "Actions" };
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        if (customerDAO.connect()) {
            for (Customer customer : customerDAO.getAllCustomers()) {
                model.addRow(new Object[] { customer.getId(), customer.getFirstName(), customer.getLastName(),
                        customer.getAddress(), customer.getPhone(), "X" });
            }
            customerDAO.disconnect();
        } else {
            JOptionPane.showMessageDialog(null, "Something went wrong when trying to connect to the database");
        }

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    selectedRow = table.getSelectedRow();

                    selectedColumn = table.getSelectedColumn();
                    if (selectedColumn == 5) {
                        int id = (int) table.getValueAt(selectedRow, 0);
                        if (customerDAO.connect()) {
                            if (customerDAO.deleteCustomer(id)) {
                                System.out.println("Customer with id " + id + " deleted successfully");
                            }
                            customerDAO.disconnect();
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Something went wrong when trying to connect to the database");
                        }
                        dispose();
                        new AddCustomerGUI();
                    }
                }
            }
        });

        add(scrollPane);

        setVisible(true);
    }
}
