package com.airtribe.meditrack.interfaces;

public interface Payable {
    double getPayableAmount();
    void processPayment();
    String getPaymentDetails();
}

