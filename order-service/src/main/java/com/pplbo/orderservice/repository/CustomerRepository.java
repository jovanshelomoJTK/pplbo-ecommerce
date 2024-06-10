package com.pplbo.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pplbo.orderservice.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    
}
