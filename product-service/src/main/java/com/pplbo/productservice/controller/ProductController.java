package com.pplbo.productservice.controller;

import com.pplbo.productservice.model.Test;
import com.pplbo.productservice.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/")
public class ProductController {

    @Autowired
    ProductService cartService;

    @GetMapping("/test")
    public List<Test> test() {
        return cartService.getAllTests();
    }
}
