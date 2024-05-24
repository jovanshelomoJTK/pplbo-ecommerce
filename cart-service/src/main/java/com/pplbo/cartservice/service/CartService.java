package com.pplbo.cartservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.cartservice.model.Test;
import com.pplbo.cartservice.repository.TestRepository;

@Service
public class CartService {

    @Autowired
    private TestRepository testRepository;

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }
}
