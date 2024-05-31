package com.pplbo.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.orderservice.event.OrderCreatedEvent;
import com.pplbo.orderservice.kafka.OrderProducer;
import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProducer orderProducer;

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order, order.getOrderStatus());

        orderProducer.sendMessage(orderCreatedEvent);

        return orderRepository.save(order);
    }
}
