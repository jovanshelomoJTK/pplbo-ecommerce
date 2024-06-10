package com.pplbo.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pplbo.cartservice.model.Cart;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByProductId(String productId);
    List<Cart> findByUserId(String userId);
    Optional<Cart> findByUserIdAndProductId(String userId, String productId);
}
