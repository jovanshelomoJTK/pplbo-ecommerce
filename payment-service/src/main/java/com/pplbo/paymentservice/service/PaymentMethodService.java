package com.pplbo.paymentservice.service;

import com.pplbo.paymentservice.model.PaymentMethod;
import com.pplbo.paymentservice.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public Optional<PaymentMethod> findById(Long id) {
        return paymentMethodRepository.findById(id);
    }
}
