package com.hotel.interfaces;

import java.util.Date;
import java.util.List;
import com.hotel.models.Reservation;

public interface IReservation {
    /**
     * Creates a new reservation
     * @param customerId The customer ID making the reservation
     * @param roomId The room ID being reserved
     * @param checkIn The check-in date
     * @param checkOut The check-out date
     * @return The newly created Reservation object
     * @pre customerId > 0 && roomId > 0 && checkIn != null && checkOut != null && checkIn.before(checkOut)
     * @pre Customer with customerId exists
     * @pre Room with roomId exists and is available for the given date range
     * @post Returns a new Reservation with a unique ID
     */
    Reservation createReservation(int customerId, int roomId, Date checkIn, Date checkOut);
    
    /**
     * Finds a reservation by its ID
     * @param reservationId The reservation ID to search for
     * @return The reservation object if found, null otherwise
     * @pre reservationId > 0
     * @post Returns reservation with given ID or null
     */
    Reservation findReservationById(int reservationId);
    
    /**
     * Finds all reservations for a specific customer
     * @param customerId The customer ID to search reservations for
     * @return List of reservations for the customer
     * @pre customerId > 0
     * @post Returns list of reservations (may be empty) for the customer
     */
    List<Reservation> findReservationsByCustomerId(int customerId);
    
    /**
     * Cancels a reservation
     * @param reservationId The reservation ID to cancel
     * @pre reservationId > 0 && findReservationById(reservationId) != null
     * @post Reservation status is set to CANCELLED
     */
    void cancelReservation(int reservationId);
    
    /**
     * Updates an existing reservation
     * @param reservation The updated reservation object
     * @pre reservation != null && findReservationById(reservation.getId()) != null
     * @post Reservation data is updated
     */
    void updateReservation(Reservation reservation);
}