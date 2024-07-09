package org.java.async.world.services;

import org.java.async.world.models.Order;

public class OrderService {

    // Simulated method to fetch an order asynchronously
    public Order getOrder(String orderId) {
        return new Order(orderId, "Pending");
    }

    // Simulated method to enrich an order
    public Order enrichOrder(Order order) {
        // Simulate enriching the order
        order.setStatus("Enriched");
        return order;
    }

    // Simulated method to perform payment
    public Order performPayment(Order order) {
        // Simulate processing payment
        order.setStatus("Paid");
        return order;
    }

    // Simulated method to dispatch order
    public Order dispatchOrder(Order order) {
        // Simulate dispatching the order
        order.setStatus("Dispatched");
        return order;
    }

    // Simulated method to send email notification
    public void sendEmail(Order order) {
        // Simulate sending email notification
    }
}

