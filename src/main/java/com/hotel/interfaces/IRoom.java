package com.hotel.interfaces;

import java.util.Date;
import java.util.List;
import com.hotel.models.Room;

public interface IRoom {
    /**
     * Finds a room by its ID
     * @param roomId The room ID to search for
     * @return The room object if found, null otherwise
     * @pre roomId > 0
     * @post Returns room with given ID or null
     */
    Room findRoomById(int roomId);
    
    /**
     * Finds available rooms for the given date range
     * @param checkIn The check-in date
     * @param checkOut The check-out date
     * @return List of available rooms
     * @pre checkIn != null && checkOut != null && checkIn.before(checkOut)
     * @post Returns a list of rooms available for the given date range (may be empty)
     */
    List<Room> findAvailableRooms(Date checkIn, Date checkOut);
    
    /**
     * Checks if a specific room is available for the given date range
     * @param roomId The room ID to check
     * @param checkIn The check-in date
     * @param checkOut The check-out date
     * @return true if the room is available, false otherwise
     * @pre roomId > 0 && checkIn != null && checkOut != null && checkIn.before(checkOut)
     * @post Returns whether room is available for the date range
     */
    boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut);
    
    /**
     * Updates a room's availability status
     * @param roomId The room ID to update
     * @param isAvailable The new availability status
     * @pre roomId > 0 && findRoomById(roomId) != null
     * @post Room's availability status is updated
     */
    void updateRoomStatus(int roomId, boolean isAvailable);
}