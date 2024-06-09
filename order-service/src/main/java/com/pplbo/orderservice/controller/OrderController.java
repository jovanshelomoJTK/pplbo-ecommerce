package com.pplbo.orderservice.controller;

import com.pplbo.orderservice.model.Test;
import com.pplbo.orderservice.service.OrderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService cartService;

    @GetMapping("/test")
    public List<Test> test() {
        return cartService.getAllTests();
    }
}
