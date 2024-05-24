package com.pplbo.paymentservice.controller;

import com.pplbo.paymentservice.model.Test;
import com.pplbo.paymentservice.service.PaymentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class PaymentController {

    @Autowired
    PaymentService cartService;

    @GetMapping("/test")
    public List<Test> test() {
        return cartService.getAllTests();
    }
}
