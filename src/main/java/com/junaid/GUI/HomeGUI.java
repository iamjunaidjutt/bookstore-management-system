package com.junaid.GUI;

import java.awt.event.*;
import javax.swing.*;

public class HomeGUI extends JFrame implements ActionListener {
    JButton addCustomerButton;
    JButton addBookButton;
    JButton placeOrderButton;
    JButton showOrdersButton;

    public HomeGUI() {
        super("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 800);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        addCustomerButton = new JButton("Add New Customer");
        addCustomerButton.setFocusable(false);
        addBookButton = new JButton("Add New Book");
        addBookButton.setFocusable(false);
        placeOrderButton = new JButton("Place Order");
        placeOrderButton.setFocusable(false);
        addBookButton.setFocusable(false);
        showOrdersButton = new JButton("Show Orders");
        showOrdersButton.setFocusable(false);

        addCustomerButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        addBookButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        placeOrderButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        showOrdersButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        addCustomerButton.addActionListener(this);
        addBookButton.addActionListener(this);
        placeOrderButton.addActionListener(this);
        showOrdersButton.addActionListener(this);

        add(Box.createVerticalGlue());

        add(addCustomerButton);
        add(Box.createVerticalStrut(10));
        add(addBookButton);
        add(Box.createVerticalStrut(10));
        add(placeOrderButton);
        add(Box.createVerticalStrut(10));
        add(showOrdersButton);
        add(Box.createVerticalGlue());

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCustomerButton) {
            AddCustomerGUI addCustomerGUI = new AddCustomerGUI();
            addCustomerGUI.setVisible(true);
            dispose();
        } else if (e.getSource() == addBookButton) {
            AddBookGUI addBookGUI = new AddBookGUI();
            addBookGUI.setVisible(true);
            dispose();
        } else if (e.getSource() == placeOrderButton) {
            PlaceOrderGUI placeOrderGUI = new PlaceOrderGUI();
            placeOrderGUI.setVisible(true);
            dispose();
        } else if (e.getSource() == showOrdersButton) {
            ShowOrdersGUI showOrdersGUI = new ShowOrdersGUI();
            showOrdersGUI.setVisible(true);
            dispose();
        }
    }

}
