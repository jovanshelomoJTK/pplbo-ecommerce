package com.pplbo.orderservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pplbo.orderservice.model.Shipping;
import com.pplbo.orderservice.repository.ShippingRepository;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    public List<Shipping> findAll() {
        return shippingRepository.findAll();
    }

    public Optional<Shipping> findById(Long id) {
        return shippingRepository.findById(id);
    }

    public Shipping save(Shipping shipping) {
        return shippingRepository.save(shipping);
    }

    public void deleteById(Long id) {
        shippingRepository.deleteById(id);
    }
}
