package com.pplbo.cartservice.controller;

import com.pplbo.cartservice.model.Test;
import com.pplbo.cartservice.service.CartService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/test")
    public List<Test> test() {
        return cartService.getAllTests();
    }
}
