package com.pplbo.orderservice.sagas;

import com.pplbo.orderservice.model.Order;
import com.pplbo.orderservice.sagas.createorder.CreateOrderSagaData;
import com.pplbo.orderservice.service.OrderService;

import com.pplbo.orderservice.sagaparticipants.*;

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
                .invokeParticipant(this::validateStock)
                .onReply(OutOfStock.class, this::handleOutOfStock)
                .withCompensation(this::reverseStock)
            .step()
                .invokeParticipant(this::validateFreeShippingPromotion)
            .step()
                .invokeParticipant(this::applyFreeShipping)
            .step()
                .invokeParticipant(this::createPayment)
                .onReply(PaymentFailed.class, this::handlePaymentFailed)
                .onReply(PaymentSucceed.class, this::handlePaymentSucceed)
            .step()
                .invokeLocal(this::approve)
            .step()
                .invokeParticipant(this::removeProductFromCart)
            .build();
    
    @Override
    public SagaDefinition<CreateOrderSagaData> getSagaDefinition() {
        return this.sagaDefinition;
    }

    private void create(CreateOrderSagaData data) {
        System.out.println("SAGA CREATE ORDER RUNNING");
        Order order = orderService.createOrder(data.getOrderDetails());
        data.setOrderId(order.getOrderId());
    }

    private void reject(CreateOrderSagaData data) {
        System.out.println("SAGA REJECT ORDER RUNNING");
        orderService.cancelOrder(data.getOrderId());
    }
    
    private void approve(CreateOrderSagaData data) {
        System.out.println("SAGA APPROVE ORDER RUNNING");
        orderService.approveOrder(data.getOrderId());
    }

    private void handleOutOfStock(CreateOrderSaga data, OutOfStock reply) {
        // TODO: Implement handleOutOfStock
    }

    private void handlePaymentFailed(CreateOrderSaga data, PaymentFailed reply) {
        // TODO: Implement handlePaymentFailed
    }

    private void handlePaymentSucceed(CreateOrderSaga data, PaymentSucceed reply) {
        // TODO: Implement handlePaymentSucceed
    }
    
}
