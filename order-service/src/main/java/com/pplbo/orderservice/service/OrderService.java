package com.pplbo.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.orderservice.model.Test;
import com.pplbo.orderservice.repository.TestRepository;

@Service
public class OrderService {

    @Autowired
    private TestRepository testRepository;

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }
}
