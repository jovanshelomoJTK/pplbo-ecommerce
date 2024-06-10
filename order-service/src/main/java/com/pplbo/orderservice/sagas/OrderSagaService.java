package com.pplbo.orderservice.sagas;

import org.springframework.transaction.annotation.Transactional;

import com.pplbo.orderservice.common.OrderDetails;
import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.repository.OrderRepository;
import com.pplbo.orderservice.sagas.createorder.CreateOrderSagaData;

import io.eventuate.tram.sagas.orchestration.SagaInstanceFactory;

public class OrderSagaService {
    private final OrderRepository orderRepository;

    private final SagaInstanceFactory sagaInstanceFactory;

    private final CreateOrderSaga createOrderSaga;

    public OrderSagaService(OrderRepository orderRepository, SagaInstanceFactory sagaInstanceFactory, CreateOrderSaga createOrderSaga) {
        this.orderRepository = orderRepository;
        this.sagaInstanceFactory = sagaInstanceFactory;
        this.createOrderSaga = createOrderSaga;
    }

    @Transactional
    public Order createOrder(OrderDetails orderDetails) {
        CreateOrderSagaData data = new CreateOrderSagaData(orderDetails);
        sagaInstanceFactory.create(createOrderSaga, data);
        return orderRepository.findById(data.getOrderId()).get();
    }

}
