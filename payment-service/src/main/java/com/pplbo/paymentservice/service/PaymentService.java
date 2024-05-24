package com.pplbo.paymentservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.paymentservice.model.Test;
import com.pplbo.paymentservice.repository.TestRepository;

@Service
public class PaymentService {

    @Autowired
    private TestRepository testRepository;

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }
}
