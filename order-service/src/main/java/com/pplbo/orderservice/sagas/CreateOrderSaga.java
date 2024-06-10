package com.pplbo.orderservice.sagas;

import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.sagas.createorder.CreateOrderSagaData;
import com.pplbo.orderservice.service.OrderService;

import io.eventuate.tram.sagas.orchestration.SagaDefinition;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;

public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaData> {
    private OrderService orderService;

    public CreateOrderSaga(OrderService orderService) {
        this.orderService = orderService;
    }

    private SagaDefinition<CreateOrderSagaData> sagaDefinition = 
            step()
                .invokeLocal(this::create)
                .withCompensation(this::reject)
            .step()
                .invokeLocal(this::approve)
            .build();
    
    @Override
    public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
        return this.sagaDefinition;
    }

    private void create(CreateOrderSagaData data) {
        System.out.println("SAGAAAA CREATE ORDERRRR RUNNING");
        Order order = orderService.createOrder(data.getOrderDetails());
        data.setOrderId(order.getOrderId());
    }

    private void reject(CreateOrderSagaData data) {
        System.out.println("SAGAAAA REJECTTT ORDERRRR RUNNING");
        orderService.cancelOrder(data.getOrderId());
    }
    
    private void approve(CreateOrderSagaData data) {
        System.out.println("SAGAAAA APPROVEE ORDERRRR RUNNING");
        orderService.approveOrder(data.getOrderId());
    }
    
}
