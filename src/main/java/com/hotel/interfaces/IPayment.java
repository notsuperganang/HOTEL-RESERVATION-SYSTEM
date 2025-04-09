package com.hotel.interfaces;

import com.hotel.models.Payment;

public interface IPayment {
    /**
     * Processes a payment for a reservation
     * @param reservationId The reservation ID to process payment for
     * @param amount The payment amount
     * @param paymentMethod The payment method used
     * @return The newly created Payment object
     * @pre reservationId > 0 && amount > 0 && paymentMethod != null
     * @post Returns a new Payment with a unique ID
     */
    Payment processPayment(int reservationId, double amount, String paymentMethod);
    
    /**
     * Refunds a payment
     * @param paymentId The payment ID to refund
     * @return true if refund was successful, false otherwise
     * @pre paymentId > 0 && findPaymentById(paymentId) != null
     * @post Payment status is set to REFUNDED if successful
     */
    boolean refundPayment(int paymentId);
    
    /**
     * Finds a payment by its ID
     * @param paymentId The payment ID to search for
     * @return The payment object if found, null otherwise
     * @pre paymentId > 0
     * @post Returns payment with given ID or null
     */
    Payment findPaymentById(int paymentId);
}