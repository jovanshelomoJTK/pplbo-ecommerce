package com.pplbo.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.orderservice.common.OrderDetails;
import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public Order createOrder(OrderDetails orderDetails) {
        // OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order,
        // order.getOrderStatus());
        // kafkaTemplate.send("order-topic", orderCreatedEvent);
        System.out.println("Order created event sent");
        Order order = Order.createOrder(orderDetails);
        return orderRepository.save(order);
    }

    public void approveOrder(Long orderId) {
        orderRepository.findById(orderId).get().paidOrder();
    }

    public void cancelOrder(Long orderId) {
        orderRepository.findById(orderId).get().cancelOrder();
    }
}
