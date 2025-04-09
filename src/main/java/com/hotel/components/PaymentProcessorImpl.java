package com.hotel.components;

import java.util.HashMap;
import java.util.Map;
import com.hotel.interfaces.IPayment;
import com.hotel.models.Payment;

/**
 * Implementation of the Payment Processor component
 */
public class PaymentProcessorImpl implements IPayment {
    private Map<Integer, Payment> payments = new HashMap<>();
    private int nextId = 1;
    
    @Override
    public Payment processPayment(int reservationId, double amount, String paymentMethod) {
        if (reservationId <= 0) {
            throw new IllegalArgumentException("Reservation ID must be positive");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be empty");
        }
        
        Payment payment = new Payment(nextId++, reservationId, amount, paymentMethod);
        payments.put(payment.getId(), payment);
        return payment;
    }
    
    @Override
    public boolean refundPayment(int paymentId) {
        if (paymentId <= 0) {
            throw new IllegalArgumentException("Payment ID must be positive");
        }
        
        Payment payment = findPaymentById(paymentId);
        if (payment == null) {
            return false;
        }
        
        payment.setStatus("REFUNDED");
        payments.put(paymentId, payment);
        return true;
    }
    
    @Override
    public Payment findPaymentById(int paymentId) {
        if (paymentId <= 0) {
            throw new IllegalArgumentException("Payment ID must be positive");
        }
        return payments.get(paymentId);
    }
}