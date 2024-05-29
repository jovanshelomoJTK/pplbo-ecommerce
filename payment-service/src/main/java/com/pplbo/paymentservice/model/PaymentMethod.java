package com.pplbo.paymentservice.model;

public enum PaymentMethod {
    CREDIT_DEBIT_CARDS("Credit/Debit Cards"),
    DIGITAL_WALLETS("Digital Wallets"),
    BANK_TRANSFERS("Bank Transfers");

    private String method;

    PaymentMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
