package com.pplbo.paymentservice.model;

public enum PaymentMethod {
    CREDIT_DEBIT_CARDS("CREDIT_DEBIT_CARDS"),
    DIGITAL_WALLETS("DIGITAL_WALLETS"),
    BANK_TRANSFERS("BANK_TRANSFERS");

    private String method;

    PaymentMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
