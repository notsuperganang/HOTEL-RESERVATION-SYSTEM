# 🏨 Sistem Reservasi Hotel

![Sistem Reservasi Hotel](https://img.shields.io/badge/Proyek-Sistem%20Reservasi%20Hotel-blue)
![Java](https://img.shields.io/badge/Bahasa-Java-orange)
![Component-Based](https://img.shields.io/badge/Arsitektur-Berbasis%20Komponen-green)
![Version](https://img.shields.io/badge/Versi-1.0-red)

Sistem reservasi hotel yang tangguh dan berbasis komponen yang dibangun dengan Java. Sistem ini mendemonstrasikan kekuatan prinsip-prinsip rekayasa perangkat lunak berbasis komponen (CBSE), dengan fitur kopling longgar, kohesi tinggi, dan desain modular.

## 🌟 Fitur

- **Manajemen Pelanggan** - Registrasi dan kelola informasi pelanggan
- **Manajemen Kamar** - Lacak ketersediaan kamar, tipe, dan harga
- **Sistem Reservasi** - Buat, lihat, dan batalkan reservasi
- **Pemrosesan Pembayaran** - Menangani berbagai metode pembayaran dengan aman
- **Layanan Notifikasi** - Menjaga pelanggan terinformasi tentang reservasi mereka

## 🏗️ Arsitektur

Proyek ini dibangun menggunakan arsitektur berbasis komponen, di mana setiap komponen:
- Memiliki antarmuka yang terdefinisi dengan baik (provided dan required)
- Dapat dikembangkan, diuji, dan dipelihara secara independen
- Mengikuti prinsip Design by Contract
- Saling terhubung melalui antarmuka eksplisit

### 🧩 Komponen

| Komponen | Deskripsi | Antarmuka |
|----------|-----------|-----------|
| Customer Manager | Mengelola data pelanggan | ICustomer |
| Room Manager | Mengelola inventaris kamar | IRoom |
| Reservation Manager | Menangani proses pemesanan | IReservation |
| Payment Processor | Memproses pembayaran | IPayment |
| Notification Service | Mengirim notifikasi | INotification |

## 🚀 Memulai

### Prasyarat

- Java JDK 8 atau lebih tinggi
- Java compiler (javac)

### Instalasi & Menjalankan

1. Clone repositori:
   ```bash
   git clone https://github.com/notsuperganang/HOTEL-RESERVATION-SYSTEM.git
   ```

2. Navigasi ke direktori proyek:
   ```bash
   cd sistem-reservasi-hotel
   ```

3. Kompilasi proyek:
   ```bash
   javac -d out src/main/java/com/hotel/app/HotelReservationSystem.java src/main/java/com/hotel/components/*.java src/main/java/com/hotel/interfaces/*.java src/main/java/com/hotel/models/*.java
   ```

4. Jalankan aplikasi:
   ```bash
   java -cp out com.hotel.app.HotelReservationSystem
   ```

## 💼 Penggunaan

Sistem menyediakan antarmuka command-line dengan opsi berikut:

1. **Registrasi Pelanggan Baru** - Buat profil pelanggan baru
2. **Buat Reservasi** - Pesan kamar untuk pelanggan yang terdaftar
3. **Cek Reservasi** - Lihat reservasi yang ada
4. **Batalkan Reservasi** - Batalkan reservasi yang ada
5. **Keluar** - Tutup aplikasi

## 📂 Struktur Proyek

```
.
└── src
    └── main
        └── java
            └── com
                └── hotel
                    ├── app
                    │   └── HotelReservationSystem.java
                    ├── components
                    │   ├── CustomerManagerImpl.java
                    │   ├── NotificationServiceImpl.java
                    │   ├── PaymentProcessorImpl.java
                    │   ├── ReservationManagerImpl.java
                    │   └── RoomManagerImpl.java
                    ├── interfaces
                    │   ├── ICustomer.java
                    │   ├── INotification.java
                    │   ├── IPayment.java
                    │   ├── IReservation.java
                    │   └── IRoom.java
                    └── models
                        ├── Customer.java
                        ├── Payment.java
                        ├── Reservation.java
                        └── Room.java
```

## 📋 Prinsip Desain yang Diterapkan

- **Arsitektur Berbasis Komponen** - Sistem dibangun dari komponen yang saling terhubung dan dapat digunakan kembali
- **Design by Contract** - Prekondisi dan poskondisi eksplisit untuk setiap metode
- **Dependency Injection** - Komponen menerima dependensi mereka melalui konstruktor
- **Interface Segregation** - Antarmuka yang bersih dan terfokus untuk setiap tanggung jawab
- **High Cohesion, Low Coupling** - Komponen fokus pada tanggung jawab tunggal dan kopling longgar

## 👥 Pola Desain

- **Facade Pattern** - `HotelReservationSystem` bertindak sebagai fasad untuk komponen yang mendasari
- **Dependency Injection** - Komponen menerima antarmuka yang diperlukan melalui konstruktor
- **Repository Pattern** - Digunakan untuk akses data di komponen manajer

## 🔍 Contoh Interaksi

```
===== SISTEM RESERVASI HOTEL =====
1. Registrasi Pelanggan Baru
2. Buat Reservasi
3. Cek Reservasi
4. Batalkan Reservasi
5. Keluar

Pilih menu: 1

----- REGISTRASI PELANGGAN BARU -----
Nama: John Doe
Email: john@example.com
Nomor Telepon: +62-123-456-7890

Pelanggan berhasil didaftarkan dengan ID: 1
Informasi Pelanggan:
Nama: John Doe
Email: john@example.com
Telepon: +62-123-456-7890
```

## 🎓 Informasi Akademik

Proyek ini dikembangkan sebagai bagian dari mata kuliah **Perangkat Lunak Berbasis Komponen** di **Universitas Syiah Kuala**. Proyek ini mendemonstrasikan aplikasi praktis dari prinsip-prinsip pengembangan perangkat lunak berbasis komponen, desain antarmuka, dan arsitektur perangkat lunak.

## 📝 Lisensi

Proyek ini tersedia untuk tujuan akademik dan pembelajaran.

## ✨ Penghargaan

Terima kasih khusus kepada Bapak Kurnia Saputra, ST, M.Sc. atas bimbingan dan pengajaran prinsip-prinsip Perangkat Lunak Berbasis Komponen yang membuat proyek ini mungkin.

---

**Catatan:** Ini adalah proyek demonstrasi sederhana dan tidak dimaksudkan untuk penggunaan produksi tanpa implementasi keamanan, persistensi, dan fitur tambahan.