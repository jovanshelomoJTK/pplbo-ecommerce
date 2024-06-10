package com.pplbo.orderservice.repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.pplbo.orderservice.service.OrderService;
import com.pplbo.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class OrderDomainConfiguration {

  // @Autowired
  // OrderRepository orderRepository;

  @Bean
  public OrderService orderService(OrderRepository orderRepository) {
    return new OrderService(orderRepository);
  }
}
