package manager;

import model.Booking;
import model.Customer;
import model.Schedule;
import model.Passenger;
import exception.ValidationException;

import java.time.LocalDateTime;

// BUILDER PATTERN untuk membuat object Booking
// Memudahkan pembuatan object kompleks dengan banyak parameter
// Author: Habibi Putra Rizqullah (2411531001)
public class BookingBuilder {
    private String bookingId;
    private Customer customer;
    private Schedule schedule;
    private Passenger passenger;
    private int jumlahSeat;
    private double totalHarga;
    private String statusBayar;
    private LocalDateTime tanggalBooking;
    
    public BookingBuilder() {
        // Set default values
        this.statusBayar = "BELUM_BAYAR";
        this.tanggalBooking = LocalDateTime.now();
    }
    
    // Method chaining untuk set booking ID
    public BookingBuilder setBookingId(String bookingId) {
        this.bookingId = bookingId;
        return this;
    }
    
    // Method chaining untuk set customer
    public BookingBuilder setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }
    
    // Method chaining untuk set schedule
    public BookingBuilder setSchedule(Schedule schedule) {
        this.schedule = schedule;
        return this;
    }
    
    // Method chaining untuk set passenger
    public BookingBuilder setPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }
    
    // Method chaining untuk set jumlah seat
    public BookingBuilder setJumlahSeat(int jumlahSeat) {
        this.jumlahSeat = jumlahSeat;
        return this;
    }
    
    // Method chaining untuk set total harga
    public BookingBuilder setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
        return this;
    }
    
    // Method chaining untuk set status bayar
    public BookingBuilder setStatusBayar(String statusBayar) {
        this.statusBayar = statusBayar;
        return this;
    }
    
    // Method chaining untuk set tanggal booking
    public BookingBuilder setTanggalBooking(LocalDateTime tanggalBooking) {
        this.tanggalBooking = tanggalBooking;
        return this;
    }
    
    // Calculate total harga berdasarkan schedule dan jumlah seat
    public BookingBuilder calculateTotalHarga() {
        if (schedule != null && jumlahSeat > 0) {
            this.totalHarga = schedule.getHarga() * jumlahSeat;
        }
        return this;
    }
    
    // Validate booking data sebelum build
    private void validate() throws ValidationException {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new ValidationException("Booking ID tidak boleh kosong!");
        }
        
        if (customer == null) {
            throw new ValidationException("Customer tidak boleh kosong!");
        }
        
        if (schedule == null) {
            throw new ValidationException("Schedule tidak boleh kosong!");
        }
        
        if (passenger == null) {
            throw new ValidationException("Data penumpang tidak boleh kosong!");
        }
        
        if (jumlahSeat <= 0) {
            throw new ValidationException("Jumlah seat harus lebih dari 0!");
        }
        
        if (!schedule.isSeatAvailable(jumlahSeat)) {
            throw new ValidationException("Seat tidak tersedia! Tersedia: " + schedule.getSeatTersedia());
        }
        
        if (totalHarga <= 0) {
            throw new ValidationException("Total harga tidak valid!");
        }
        
        if (!passenger.isValidKTP()) {
            throw new ValidationException("Nomor KTP tidak valid! Harus 16 digit.");
        }
        
        if (!passenger.isValidHP()) {
            throw new ValidationException("Nomor HP tidak valid! Harus 10-13 digit.");
        }
    }
    
    // Build Booking object dengan validasi
    public Booking build() throws ValidationException {
        validate();
        
        Booking booking = new Booking(
            bookingId,
            customer,
            schedule,
            passenger,
            jumlahSeat,
            totalHarga,
            statusBayar,
            tanggalBooking
        );
        
        return booking;
    }
    
    // Reset builder untuk digunakan kembali
    public void reset() {
        this.bookingId = null;
        this.customer = null;
        this.schedule = null;
        this.passenger = null;
        this.jumlahSeat = 0;
        this.totalHarga = 0;
        this.statusBayar = "BELUM_BAYAR";
        this.tanggalBooking = LocalDateTime.now();
    }
}