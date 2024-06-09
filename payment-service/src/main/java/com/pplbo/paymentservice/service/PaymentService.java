package com.pplbo.paymentservice.service;

import com.pplbo.paymentservice.dto.PaymentRequestDTO;
import com.pplbo.paymentservice.model.Payment;
import com.pplbo.paymentservice.model.PaymentMethod;
import com.pplbo.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPayment(PaymentRequestDTO paymentRequestDTO) {
        Payment payment = new Payment();
        payment.setCustomerId(paymentRequestDTO.getCustomerId());
        payment.setOrderId(paymentRequestDTO.getOrderId());
        payment.setStatus("PENDING");
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(String kode_payment, Integer saldo, PaymentMethod paymentMethod) {
        Optional<Payment> paymentOptional = paymentRepository.findById(kode_payment);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setPaymentMethod(paymentMethod);
            // payment.setStatus("PAID");

            // Total wawadukan
            Integer total = 20000;
            if (saldo >= total) {
                    payment.setStatus("PAID");
            } else {
                payment.setStatus("FAIL");
            }
            // if (saldo >= payment.getTotal()) {
            //         payment.setStatus("PAID");
            // } else {
            //     payment.setStatus("FAIL");
            // }
            return paymentRepository.save(payment);
        } else {
            throw new RuntimeException("Payment not found");
        }
    }

    public Payment getPaymentById(String kode_payment) {
        Optional<Payment> paymentOptional = paymentRepository.findById(kode_payment);
        return paymentOptional.orElse(null);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public boolean deletePayment(String kode_payment) {
        Optional<Payment> paymentOptional = paymentRepository.findById(kode_payment);
        if (paymentOptional.isPresent()) {
            paymentRepository.deleteById(kode_payment);
            return true;
        } else {
            return false;
        }
    }
}
