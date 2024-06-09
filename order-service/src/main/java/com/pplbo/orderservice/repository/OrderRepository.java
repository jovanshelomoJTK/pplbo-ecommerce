package com.pplbo.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pplbo.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    
}
