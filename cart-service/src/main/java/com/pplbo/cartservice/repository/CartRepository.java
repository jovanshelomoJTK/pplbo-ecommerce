package com.pplbo.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pplbo.cartservice.model.Cart;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // Custom query methods can be added here
    List<Cart> findByProductName(String productName);
}
