package com.junaid.Service;

import java.util.List;
import com.junaid.DAO.BookDAO;
import com.junaid.DAO.OrderDAO;
import com.junaid.DAO.OrderDetailsDAO;
import com.junaid.Models.Order;
import com.junaid.Models.OrderDetails;

public class OrderService {
    OrderDAO orderDAO = new OrderDAO();
    OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
    BookDAO bookDAO = new BookDAO();

    public boolean placeOrder(int customerId, List<OrderDetails> orderDetails) {
        double totalPrice = 0;
        for (OrderDetails orderDetail : orderDetails) {
            if (bookDAO.connect()) {
                totalPrice += bookDAO.getBookByISBN(orderDetail.getIsbn()).getPrice() * orderDetail.getQuantity();
                bookDAO.disconnect();
            }
        }

        int orderId = -1;
        if (orderDAO.connect()) {
            Order order = new Order();
            order.setCustomerID(customerId);
            order.setTotalPrice(totalPrice);
            orderDAO.addOrder(order);
            orderId = orderDAO.getMaxOrderId();
            orderDAO.disconnect();
        }

        if (orderId == -1) {
            return false;
        }

        for (OrderDetails orderDetail : orderDetails) {
            orderDetail.setOrderId(orderId);
            if (orderDetailsDAO.connect()) {
                orderDetailsDAO.addOrderDetails(orderDetail);
                orderDetailsDAO.disconnect();
            }
            if (bookDAO.connect()) {
                bookDAO.updateBookQuantity(orderDetail.getIsbn(), orderDetail.getQuantity());
                bookDAO.disconnect();
            }
        }
        return true;
    }
}
