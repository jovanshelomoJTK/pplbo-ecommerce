package com.pplbo.orderservice.sagas;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// import io.eventuate.tram.spring.optimistic.locking.OptimisticLockingDecoratorConfiguration;
import com.pplbo.orderservice.repository.OrderRepository;
import com.pplbo.orderservice.service.OrderService;

import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@Import(OptimisticLockingDecoratorConfiguration.class)
public class OrderSagaConfiguration {
    @Bean
    public OrderSagaService orderSagaService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
        return new OrderSagaService(orderRepository, sagaInstanceFactory, createOrderSaga);
    }

    @Bean
    public CreateOrderSaga createOrderSaga(OrderService orderService) {
        return new CreateOrderSaga(orderService);
    }
}
