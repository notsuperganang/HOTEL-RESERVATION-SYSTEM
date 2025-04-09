package com.hotel.components;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hotel.interfaces.IRoom;
import com.hotel.models.Reservation;
import com.hotel.models.Room;

/**
 * Implementation of the Room Manager component
 */
public class RoomManagerImpl implements IRoom {
    private Map<Integer, Room> rooms = new HashMap<>();
    private Map<Integer, List<Reservation>> roomReservations = new HashMap<>();
    
    /**
     * Constructor that initializes some sample rooms
     */
    public RoomManagerImpl() {
        // Initialize with some rooms
        addRoom(new Room(1, "101", "Single", 100.0, true));
        addRoom(new Room(2, "102", "Double", 150.0, true));
        addRoom(new Room(3, "201", "Suite", 250.0, true));
    }
    
    /**
     * Helper method to add a room
     * @param room The room to add
     */
    private void addRoom(Room room) {
        rooms.put(room.getId(), room);
        roomReservations.put(room.getId(), new ArrayList<>());
    }
    
    @Override
    public Room findRoomById(int roomId) {
        if (roomId <= 0) {
            throw new IllegalArgumentException("Room ID must be positive");
        }
        return rooms.get(roomId);
    }
    
    @Override
    public List<Room> findAvailableRooms(Date checkIn, Date checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        if (!checkIn.before(checkOut)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms.values()) {
            if (isRoomAvailable(room.getId(), checkIn, checkOut)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    
    @Override
    public boolean isRoomAvailable(int roomId, Date checkIn, Date checkOut) {
        if (roomId <= 0) {
            throw new IllegalArgumentException("Room ID must be positive");
        }
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        if (!checkIn.before(checkOut)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        
        if (!rooms.containsKey(roomId) || !rooms.get(roomId).isAvailable()) {
            return false;
        }
        
        List<Reservation> reservations = roomReservations.get(roomId);
        for (Reservation reservation : reservations) {
            if (reservation.getStatus().equals("CANCELLED")) {
                continue;
            }
            
            // Check if there is an overlap with existing reservations
            if (!(checkOut.before(reservation.getCheckInDate()) || 
                  checkIn.after(reservation.getCheckOutDate()))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void updateRoomStatus(int roomId, boolean isAvailable) {
        if (roomId <= 0) {
            throw new IllegalArgumentException("Room ID must be positive");
        }
        
        Room room = findRoomById(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found");
        }
        
        room.setAvailable(isAvailable);
        rooms.put(roomId, room);
    }
    
    /**
     * Method to add a reservation to a room's list
     * @param reservation The reservation to add
     */
    public void addReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }
        
        int roomId = reservation.getRoomId();
        if (roomReservations.containsKey(roomId)) {
            roomReservations.get(roomId).add(reservation);
        } else {
            throw new IllegalArgumentException("Room not found");
        }
    }
}