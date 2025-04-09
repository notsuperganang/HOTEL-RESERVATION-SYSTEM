package com.hotel.models;

import java.util.Date;

/**
 * Represents a hotel reservation
 */
public class Reservation {
    private int id;
    private int customerId;
    private int roomId;
    private Date checkInDate;
    private Date checkOutDate;
    private String status; // "CONFIRMED", "CANCELLED", "CHECKED_IN", "CHECKED_OUT"
    private double totalPrice;
    
    /**
     * Constructor for Reservation
     * @param id Reservation ID
     * @param customerId Customer ID
     * @param roomId Room ID
     * @param checkInDate Check-in date
     * @param checkOutDate Check-out date
     */
    public Reservation(int id, int customerId, int roomId, Date checkInDate, Date checkOutDate) {
        this.id = id;
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = "CONFIRMED";
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }
    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    
    @Override
    public String toString() {
        return "Reservation [id=" + id + ", customerId=" + customerId + ", roomId=" + roomId + 
               ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + 
               ", status=" + status + ", totalPrice=" + totalPrice + "]";
    }
}
