package com.hotel.components;

import com.hotel.interfaces.INotification;
import com.hotel.models.Customer;
import com.hotel.models.Payment;
import com.hotel.models.Reservation;

/**
 * Implementation of the Notification Service component
 */
public class NotificationServiceImpl implements INotification {
    @Override
    public void sendConfirmationEmail(Customer customer, Reservation reservation) {
        if (customer == null || reservation == null) {
            throw new IllegalArgumentException("Customer and reservation cannot be null");
        }
        
        // In a real application, this would send an actual email
        System.out.println("Sending confirmation email to " + customer.getEmail() + 
                           " for reservation #" + reservation.getId());
    }
    
    @Override
    public void sendCancellationEmail(Customer customer, Reservation reservation) {
        if (customer == null || reservation == null) {
            throw new IllegalArgumentException("Customer and reservation cannot be null");
        }
        
        System.out.println("Sending cancellation email to " + customer.getEmail() + 
                           " for reservation #" + reservation.getId());
    }
    
    @Override
    public void sendPaymentConfirmation(Customer customer, Payment payment) {
        if (customer == null || payment == null) {
            throw new IllegalArgumentException("Customer and payment cannot be null");
        }
        
        System.out.println("Sending payment confirmation email to " + customer.getEmail() + 
                           " for payment #" + payment.getId() + 
                           " amount: $" + payment.getAmount());
    }
}