package com.hotel.interfaces;

import com.hotel.models.Customer;
import com.hotel.models.Reservation;
import com.hotel.models.Payment;

public interface INotification {
    /**
     * Sends a reservation confirmation email
     * @param customer The customer to send the email to
     * @param reservation The reservation details
     * @pre customer != null && reservation != null
     * @post Confirmation email is sent to customer
     */
    void sendConfirmationEmail(Customer customer, Reservation reservation);
    
    /**
     * Sends a reservation cancellation email
     * @param customer The customer to send the email to
     * @param reservation The reservation details
     * @pre customer != null && reservation != null
     * @post Cancellation email is sent to customer
     */
    void sendCancellationEmail(Customer customer, Reservation reservation);
    
    /**
     * Sends a payment confirmation email
     * @param customer The customer to send the email to
     * @param payment The payment details
     * @pre customer != null && payment != null
     * @post Payment confirmation email is sent to customer
     */
    void sendPaymentConfirmation(Customer customer, Payment payment);
}
