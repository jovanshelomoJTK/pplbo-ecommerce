package com.pplbo.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.pplbo.orderservice.event.OrderCreatedEvent;
import com.pplbo.orderservice.kafka.KafkaProducer;
import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.repository.OrderRepository;

@Service
public class OrderService {
    // private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Autowired
    private OrderRepository orderRepository;

    private KafkaProducer kafkaProducer;

    public OrderService(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    // public OrderService(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
    //     this.kafkaTemplate = kafkaTemplate;
    // }

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(order, order.getOrderStatus());
        // kafkaTemplate.send("order-topic", orderCreatedEvent);
        kafkaProducer.sendMessage("order-topic", orderCreatedEvent.toString());
        return orderRepository.save(order);
    }
}
