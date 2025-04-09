package com.hotel.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.hotel.components.CustomerManagerImpl;
import com.hotel.components.NotificationServiceImpl;
import com.hotel.components.PaymentProcessorImpl;
import com.hotel.components.ReservationManagerImpl;
import com.hotel.components.RoomManagerImpl;
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
 * Main application class for the Hotel Reservation System
 */
public class HotelReservationSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    // Component interfaces
    private static ICustomer customerManager;
    private static IRoom roomManager;
    private static IPayment paymentProcessor;
    private static INotification notificationService;
    private static IReservation reservationManager;
    
    public static void main(String[] args) {
        // Initialize and connect components
        initializeComponents();
        
        boolean exit = false;
        
        while (!exit) {
            displayMainMenu();
            int choice = getIntInput("Pilih menu: ");
            
            switch (choice) {
                case 1:
                    registerCustomer();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    checkReservations();
                    break;
                case 4:
                    cancelReservation();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Terima kasih telah menggunakan sistem Reservasi Hotel!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Initialize and connect all components
     */
    private static void initializeComponents() {
        // Create component instances
        customerManager = new CustomerManagerImpl();
        roomManager = new RoomManagerImpl();
        paymentProcessor = new PaymentProcessorImpl();
        notificationService = new NotificationServiceImpl();
        
        // Dependency injection for ReservationManager
        reservationManager = new ReservationManagerImpl(
            customerManager, roomManager, paymentProcessor, notificationService);
    }
    
    /**
     * Display the main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n===== SISTEM RESERVASI HOTEL =====");
        System.out.println("1. Registrasi Pelanggan Baru");
        System.out.println("2. Buat Reservasi");
        System.out.println("3. Cek Reservasi");
        System.out.println("4. Batalkan Reservasi");
        System.out.println("5. Keluar");
    }
    
    /**
     * Register a new customer
     */
    private static void registerCustomer() {
        System.out.println("\n----- REGISTRASI PELANGGAN BARU -----");
        String name = getStringInput("Nama: ");
        String email = getStringInput("Email: ");
        String phone = getStringInput("Nomor Telepon: ");
        
        try {
            Customer customer = customerManager.createCustomer(name, email, phone);
            System.out.println("Pelanggan berhasil didaftarkan dengan ID: " + customer.getId());
            System.out.println("Informasi Pelanggan:");
            System.out.println("Nama: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Telepon: " + customer.getPhone());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Make a new reservation
     */
    private static void makeReservation() {
        System.out.println("\n----- BUAT RESERVASI BARU -----");
        int customerId = getIntInput("ID Pelanggan: ");
        
        try {
            Customer customer = customerManager.findCustomerById(customerId);
            if (customer == null) {
                System.out.println("Pelanggan dengan ID " + customerId + " tidak ditemukan.");
                return;
            }
            
            System.out.println("Pelanggan ditemukan: " + customer.getName());
            
            Date checkIn = getDateInput("Tanggal Check-in (yyyy-MM-dd): ");
            Date checkOut = getDateInput("Tanggal Check-out (yyyy-MM-dd): ");
            
            List<Room> availableRooms = roomManager.findAvailableRooms(checkIn, checkOut);
            
            if (availableRooms.isEmpty()) {
                System.out.println("Tidak ada kamar tersedia untuk tanggal yang dipilih.");
                return;
            }
            
            displayAvailableRooms(availableRooms);
            
            int roomChoice = getIntInput("Pilih nomor kamar (1-" + availableRooms.size() + "): ");
            if (roomChoice < 1 || roomChoice > availableRooms.size()) {
                System.out.println("Pilihan tidak valid.");
                return;
            }
            
            Room selectedRoom = availableRooms.get(roomChoice - 1);
            
            Reservation reservation = reservationManager.createReservation(
                customer.getId(), selectedRoom.getId(), checkIn, checkOut);
            
            System.out.println("\nReservasi berhasil dibuat!");
            System.out.println("ID Reservasi: " + reservation.getId());
            System.out.println("Tanggal Check-in: " + dateFormat.format(checkIn));
            System.out.println("Tanggal Check-out: " + dateFormat.format(checkOut));
            System.out.println("Total Harga: $" + reservation.getTotalPrice());
            
            processPaymentForReservation(reservation.getId());
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Display available rooms
     * @param availableRooms List of available rooms
     */
    private static void displayAvailableRooms(List<Room> availableRooms) {
        System.out.println("\nKamar tersedia:");
        for (int i = 0; i < availableRooms.size(); i++) {
            Room room = availableRooms.get(i);
            System.out.println((i + 1) + ". Kamar " + room.getRoomNumber() + 
                             " - Tipe: " + room.getType() + 
                             " - Harga: $" + room.getPricePerNight() + "/malam");
        }
    }
    
    /**
     * Process payment for a reservation
     * @param reservationId Reservation ID to process payment for
     */
    private static void processPaymentForReservation(int reservationId) {
        System.out.println("\nProses pembayaran...");
        System.out.println("Pilih Metode Pembayaran:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. Bank Transfer");
        System.out.println("4. Cash");

        int paymentChoice = getIntInput("Pilih metode pembayaran (1-4): ");
        String paymentMethod;

        switch (paymentChoice) {
            case 1:
                paymentMethod = "Credit Card";
                break;
            case 2:
                paymentMethod = "Debit Card";
                break;
            case 3:
                paymentMethod = "Bank Transfer";
                break;
            case 4:
                paymentMethod = "Cash";
                break;
            default:
                System.out.println("Pilihan tidak valid. Menggunakan Credit Card sebagai default.");
                paymentMethod = "Credit Card";
        }

        try {
            if (reservationManager instanceof ReservationManagerImpl) {
                Payment payment = ((ReservationManagerImpl) reservationManager)
                    .processPayment(reservationId, paymentMethod);

                System.out.println("Pembayaran berhasil diproses!");
                System.out.println("ID Pembayaran: " + payment.getId());
                System.out.println("Metode Pembayaran: " + payment.getPaymentMethod());
                System.out.println("Status: " + payment.getStatus());
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error memproses pembayaran: " + e.getMessage());
        }
    }
    
    /**
     * Check reservations for a customer
     */
    private static void checkReservations() {
        System.out.println("\n----- CEK RESERVASI -----");
        int customerId = getIntInput("ID Pelanggan: ");
        
        try {
            Customer customer = customerManager.findCustomerById(customerId);
            if (customer == null) {
                System.out.println("Pelanggan dengan ID " + customerId + " tidak ditemukan.");
                return;
            }
            
            List<Reservation> reservations = reservationManager.findReservationsByCustomerId(customerId);
            
            if (reservations.isEmpty()) {
                System.out.println("Tidak ada reservasi untuk pelanggan ini.");
                return;
            }
            
            System.out.println("\nDaftar Reservasi untuk " + customer.getName() + ":");
            for (Reservation reservation : reservations) {
                displayReservationDetails(reservation, customer);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Display reservation details
     * @param reservation Reservation to display
     * @param customer Customer who made the reservation
     */
    private static void displayReservationDetails(Reservation reservation, Customer customer) {
        Room room = roomManager.findRoomById(reservation.getRoomId());
        System.out.println("------------------------------");
        System.out.println("ID Reservasi: " + reservation.getId());
        System.out.println("Kamar: " + room.getRoomNumber() + " (" + room.getType() + ")");
        System.out.println("Check-in: " + dateFormat.format(reservation.getCheckInDate()));
        System.out.println("Check-out: " + dateFormat.format(reservation.getCheckOutDate()));
        System.out.println("Status: " + reservation.getStatus());
        System.out.println("Total: $" + reservation.getTotalPrice());
        
        // Display payment information if available
        if (reservationManager instanceof ReservationManagerImpl) {
            Payment payment = ((ReservationManagerImpl) reservationManager)
                .getPaymentForReservation(reservation.getId());
                
            if (payment != null) {
                System.out.println("Metode Pembayaran: " + payment.getPaymentMethod());
                System.out.println("Status Pembayaran: " + payment.getStatus());
            }
        }
    }
    
    /**
     * Cancel a reservation
     */
    private static void cancelReservation() {
        System.out.println("\n----- BATALKAN RESERVASI -----");
        int reservationId = getIntInput("ID Reservasi: ");
        
        try {
            Reservation reservation = reservationManager.findReservationById(reservationId);
            if (reservation == null) {
                System.out.println("Reservasi dengan ID " + reservationId + " tidak ditemukan.");
                return;
            }
            
            if (reservation.getStatus().equals("CANCELLED")) {
                System.out.println("Reservasi ini sudah dibatalkan sebelumnya.");
                return;
            }
            
            Customer customer = customerManager.findCustomerById(reservation.getCustomerId());
            Room room = roomManager.findRoomById(reservation.getRoomId());
            
            System.out.println("\nDetail Reservasi:");
            System.out.println("Pelanggan: " + customer.getName());
            System.out.println("Kamar: " + room.getRoomNumber() + " (" + room.getType() + ")");
            System.out.println("Check-in: " + dateFormat.format(reservation.getCheckInDate()));
            System.out.println("Check-out: " + dateFormat.format(reservation.getCheckOutDate()));
            System.out.println("Total: $" + reservation.getTotalPrice());
            
            // Display payment method if available
            if (reservationManager instanceof ReservationManagerImpl) {
                Payment payment = ((ReservationManagerImpl) reservationManager)
                    .getPaymentForReservation(reservation.getId());
                    
                if (payment != null) {
                    System.out.println("Metode Pembayaran: " + payment.getPaymentMethod());
                }
            }
            
            String confirm = getStringInput("Yakin ingin membatalkan reservasi ini? (y/n): ");
            if (confirm.equalsIgnoreCase("y")) {
                reservationManager.cancelReservation(reservationId);
                System.out.println("Reservasi berhasil dibatalkan.");
            } else {
                System.out.println("Pembatalan reservasi dibatalkan.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // Helper methods for user input
    
    /**
     * Get integer input from user
     * @param prompt Prompt to display
     * @return User input as integer
     */
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Input tidak valid. Masukkan angka.");
            System.out.print(prompt);
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return input;
    }
    
    /**
     * Get string input from user
     * @param prompt Prompt to display
     * @return User input as string
     */
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    /**
     * Get date input from user
     * @param prompt Prompt to display
     * @return User input as Date object
     */
    private static Date getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return dateFormat.parse(input);
            } catch (ParseException e) {
                System.out.println("Format tanggal tidak valid. Gunakan format yyyy-MM-dd.");
            }
        }
    }
}
