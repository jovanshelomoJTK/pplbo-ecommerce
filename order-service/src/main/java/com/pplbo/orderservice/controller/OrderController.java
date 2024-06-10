package com.pplbo.orderservice.controller;

import com.pplbo.orderservice.common.OrderDetails;
import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.sagas.OrderSagaService;
import com.pplbo.orderservice.service.OrderService;
import com.pplbo.orderservice.web.CreateOrderRequest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderSagaService orderSagaService;

    @GetMapping("/orders")
    public List<Order> getAllOrder() {
        return orderService.getAllOrder();
    }

    @PostMapping("/order")
    public Order createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        // return orderService
        //         .createOrder(new OrderDetails(createOrderRequest.getCustomerId(), createOrderRequest.getOrderTotal(),
        //                 createOrderRequest.getShippingAddress()));
        //                 // createOrderRequest.getOrderItems()));

        return orderSagaService.createOrder(new OrderDetails(createOrderRequest.getCustomerId(), createOrderRequest.getOrderTotal(),
                        createOrderRequest.getShippingAddress()));
                        // createOrderRequest.getOrderItems();

    }
}
