package com.pplbo.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.productservice.model.Test;
import com.pplbo.productservice.repository.TestRepository;

@Service
public class ProductService {

    @Autowired
    private TestRepository testRepository;

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }
}
