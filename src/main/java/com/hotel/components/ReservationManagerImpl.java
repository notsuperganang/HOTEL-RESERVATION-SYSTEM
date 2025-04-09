package com.hotel.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hotel.interfaces.ICustomer;
import com.hotel.interfaces.INotification;
import com.hotel.interfaces.IPayment;
import com.hotel.interfaces.IReservation;
import com.hotel.interfaces.IRoom;
import com.hotel.models.Customer;
import com.hotel.models.Payment;
import com.hotel.models.Reservation;
import com.hotel.models.Room;

/**
 * Implementation of the Reservation Manager component
 */
public class ReservationManagerImpl implements IReservation {
    private Map<Integer, Reservation> reservations = new HashMap<>();
    private Map<Integer, Payment> reservationPayments = new HashMap<>();
    private int nextId = 1;
    
    // Required interfaces (dependencies)
    private final ICustomer customerManager;
    private final IRoom roomManager;
    private final IPayment paymentProcessor;
    private final INotification notificationService;
    
    /**
     * Constructor with dependency injection
     * @param customerManager Customer manager component
     * @param roomManager Room manager component
     * @param paymentProcessor Payment processor component
     * @param notificationService Notification service component
     */
    public ReservationManagerImpl(
            ICustomer customerManager, 
            IRoom roomManager, 
            IPayment paymentProcessor, 
            INotification notificationService) {
        
        this.customerManager = customerManager;
        this.roomManager = roomManager;
        this.paymentProcessor = paymentProcessor;
        this.notificationService = notificationService;
    }
    
    @Override
    public Reservation createReservation(int customerId, int roomId, Date checkIn, Date checkOut) {
        // Validate preconditions
        if (customerId <= 0 || roomId <= 0) {
            throw new IllegalArgumentException("Customer ID and Room ID must be positive");
        }
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        if (!checkIn.before(checkOut)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        
        // Validate customer exists
        Customer customer = customerManager.findCustomerById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        
        // Check room availability
        if (!roomManager.isRoomAvailable(roomId, checkIn, checkOut)) {
            throw new IllegalArgumentException("Room not available for the selected dates");
        }
        
        // Calculate total price (assuming 1 day = 1 night)
        Room room = roomManager.findRoomById(roomId);
        long diffInMillies = checkOut.getTime() - checkIn.getTime();
        int nights = (int) (diffInMillies / (1000 * 60 * 60 * 24));
        double totalPrice = room.getPricePerNight() * nights;
        
        // Create reservation
        Reservation reservation = new Reservation(nextId++, customerId, roomId, checkIn, checkOut);
        reservation.setTotalPrice(totalPrice);
        reservations.put(reservation.getId(), reservation);
        
        // Add reservation to room's list (assuming RoomManagerImpl has this method)
        if (roomManager instanceof RoomManagerImpl) {
            ((RoomManagerImpl) roomManager).addReservation(reservation);
        }
        
        // Send confirmation email
        notificationService.sendConfirmationEmail(customer, reservation);
        
        return reservation;
    }
    
    @Override
    public Reservation findReservationById(int reservationId) {
        if (reservationId <= 0) {
            throw new IllegalArgumentException("Reservation ID must be positive");
        }
        return reservations.get(reservationId);
    }
    
    @Override
    public List<Reservation> findReservationsByCustomerId(int customerId) {
        if (customerId <= 0) {
            throw new IllegalArgumentException("Customer ID must be positive");
        }
        
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations.values()) {
            if (reservation.getCustomerId() == customerId) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }
    
    @Override
    public void cancelReservation(int reservationId) {
        if (reservationId <= 0) {
            throw new IllegalArgumentException("Reservation ID must be positive");
        }
        
        Reservation reservation = findReservationById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }
        
        reservation.setStatus("CANCELLED");
        reservations.put(reservationId, reservation);
        
        // Notify customer
        Customer customer = customerManager.findCustomerById(reservation.getCustomerId());
        notificationService.sendCancellationEmail(customer, reservation);
    }
    
    @Override
    public void updateReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        
        if (!reservations.containsKey(reservation.getId())) {
            throw new IllegalArgumentException("Reservation not found");
        }
        
        reservations.put(reservation.getId(), reservation);
    }
    
    /**
     * Processes payment for a reservation
     * @param reservationId The reservation ID to process payment for
     * @param paymentMethod The payment method used
     * @return The created Payment object
     */
    public Payment processPayment(int reservationId, String paymentMethod) {
        Reservation reservation = findReservationById(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        Payment payment = paymentProcessor.processPayment(
            reservationId, reservation.getTotalPrice(), paymentMethod);

        // Store payment information with reservation ID
        reservationPayments.put(reservationId, payment);

        // Send payment confirmation
        Customer customer = customerManager.findCustomerById(reservation.getCustomerId());
        notificationService.sendPaymentConfirmation(customer, payment);

        return payment;
    }

    /**
     * Gets payment information for a specific reservation
     * @param reservationId The reservation ID
     * @return The Payment object if found, null otherwise
     */
    public Payment getPaymentForReservation(int reservationId) {
        return reservationPayments.get(reservationId);
    }
}