package com.pplbo.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.orderservice.common.OrderDetails;
import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.repository.OrderRepository;

import com.pplbo.orderservice.kafka.KafkaProducerService;
import com.pplbo.orderservice.event.OrderCreated;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).get();
    }

    public Order createOrder(OrderDetails orderDetails) {
        Order order = Order.createOrder(orderDetails);
        orderRepository.save(order);
        kafkaProducerService.sendMessage("order-created", new OrderCreated(order.getOrderId(), order));
        return order;
    }

    public void approveOrder(Long orderId) {
        orderRepository.findById(orderId).get().paidOrder();
    }

    public void cancelOrder(Long orderId) {
        orderRepository.findById(orderId).get().cancelOrder();
    }
}
