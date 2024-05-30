package com.pplbo.orderservice.controller;

import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.service.OrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrder() {
        return orderService.getAllOrder();
    }
}
