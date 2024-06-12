package com.pplbo.paymentservice.controller;

import com.pplbo.paymentservice.dto.PaymentRequestDTO;
import com.pplbo.paymentservice.event.PaymentRequestEvent;
import com.pplbo.paymentservice.model.Payment;
import com.pplbo.paymentservice.model.PaymentMethod;
import com.pplbo.paymentservice.service.PaymentMethodService;
import com.pplbo.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pplbo.paymentservice.jwt.customannotations.AllowedRoles;
import com.pplbo.paymentservice.jwt.customannotations.UserDataFromToken;
import com.pplbo.paymentservice.jwt.model.JwtUserData;
import com.pplbo.paymentservice.jwt.model.JwtUserData.Role;
import com.pplbo.paymentservice.kafka.KafkaProducerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @AllowedRoles({ Role.CUSTOMER })
    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestBody PaymentRequestDTO paymentRequestDTO, 
            @UserDataFromToken JwtUserData userData) {
        // Set customer_id from JWT token
        Payment createdPayment = paymentService.createPayment(Long.parseLong(userData.getId()), paymentRequestDTO);
        return ResponseEntity.ok(createdPayment);
    }

    @PutMapping("/{kodePayment}")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable String kodePayment,
            @RequestParam int saldo,
            @RequestParam Long paymentMethodId) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodService.findById(paymentMethodId);
        if (paymentMethodOptional.isPresent()) {
            Payment updatedPayment = paymentService.updatePaymentStatus(kodePayment, saldo, paymentMethodOptional.get());
            return ResponseEntity.ok(updatedPayment);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{kode_payment}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable String kode_payment) {
        Payment payment = paymentService.getPaymentById(kode_payment);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @DeleteMapping("/{kode_payment}")
    public ResponseEntity<String> deletePayment(@PathVariable String kode_payment) {
        boolean deleted = paymentService.deletePayment(kode_payment);
        if (deleted) {
            return ResponseEntity.ok("Payment deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Payment not found.");
        }
    }

    @PostMapping("/test-payment-event")
    public ResponseEntity<String> sendPaymentRequestEvent(@RequestBody PaymentRequestEvent paymentRequestEvent) {
        kafkaProducerService.sendPaymentRequestEvent(paymentRequestEvent);
        return ResponseEntity.ok("PaymentRequestEvent sent successfully.");
    }
}
