package com.hotel.models;

import java.util.Date;

/**
 * Represents a payment for a reservation
 */
public class Payment {
    private int id;
    private int reservationId;
    private double amount;
    private Date paymentDate;
    private String paymentMethod;
    private String status; // "COMPLETED", "REFUNDED", "FAILED"
    
    /**
     * Constructor for Payment
     * @param id Payment ID
     * @param reservationId Reservation ID
     * @param amount Payment amount
     * @param paymentMethod Payment method
     */
    public Payment(int id, int reservationId, double amount, String paymentMethod) {
        this.id = id;
        this.reservationId = reservationId;
        this.amount = amount;
        this.paymentDate = new Date();
        this.paymentMethod = paymentMethod;
        this.status = "COMPLETED";
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return "Payment [id=" + id + ", reservationId=" + reservationId + ", amount=" + amount + 
               ", paymentDate=" + paymentDate + ", paymentMethod=" + paymentMethod + 
               ", status=" + status + "]";
    }
}