package model;

import java.time.LocalDateTime;

// Model class untuk transaksi pemesanan
// Menerapkan ENCAPSULATION dan COMPOSITION (HAS-A relationship)
// Author: Habibi Putra Rizqullah (2411531001)
public class Booking {
    // ENCAPSULATION: atribut private
    private String bookingId;
    private Customer customer; // COMPOSITION: Booking HAS-A Customer
    private Schedule schedule; // COMPOSITION: Booking HAS-A Schedule
    private Passenger passenger; // COMPOSITION: Booking HAS-A Passenger
    private int jumlahSeat;
    private double totalHarga;
    private String statusBayar;
    private LocalDateTime tanggalBooking;
    
    public Booking() {
    }
    
    public Booking(String bookingId, Customer customer, Schedule schedule, Passenger passenger,
                   int jumlahSeat, double totalHarga, String statusBayar, LocalDateTime tanggalBooking) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.schedule = schedule;
        this.passenger = passenger;
        this.jumlahSeat = jumlahSeat;
        this.totalHarga = totalHarga;
        this.statusBayar = statusBayar;
        this.tanggalBooking = tanggalBooking;
    }
    
    // ENCAPSULATION: getter dan setter
    public String getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Schedule getSchedule() {
        return schedule;
    }
    
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    public Passenger getPassenger() {
        return passenger;
    }
    
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
    
    public int getJumlahSeat() {
        return jumlahSeat;
    }
    
    public void setJumlahSeat(int jumlahSeat) {
        this.jumlahSeat = jumlahSeat;
    }
    
    public double getTotalHarga() {
        return totalHarga;
    }
    
    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }
    
    public String getStatusBayar() {
        return statusBayar;
    }
    
    public void setStatusBayar(String statusBayar) {
        this.statusBayar = statusBayar;
    }
    
    public LocalDateTime getTanggalBooking() {
        return tanggalBooking;
    }
    
    public void setTanggalBooking(LocalDateTime tanggalBooking) {
        this.tanggalBooking = tanggalBooking;
    }
    
    // Cek apakah booking sudah dibayar
    public boolean isBayar() {
        return "LUNAS".equalsIgnoreCase(statusBayar);
    }
    
    // Konfirmasi pembayaran
    public void konfirmasiPembayaran() {
        this.statusBayar = "LUNAS";
        System.out.println("Pembayaran berhasil dikonfirmasi untuk Booking ID: " + bookingId);
    }
    
    // Cancel booking dan kembalikan seat
    public void cancelBooking() {
        this.statusBayar = "DIBATALKAN";
        if (schedule != null) {
            schedule.releaseSeat(jumlahSeat);
        }
        System.out.println("Booking ID: " + bookingId + " telah dibatalkan.");
    }
    
    // Display info booking lengkap
    public String displayInfo() {
        StringBuilder info = new StringBuilder();
        info.append("=== DETAIL BOOKING ===\n");
        info.append("Booking ID: ").append(bookingId).append("\n");
        info.append("Tanggal Booking: ").append(tanggalBooking).append("\n\n");
        
        if (customer != null) {
            info.append("Customer: ").append(customer.getFullName()).append("\n\n");
        }
        
        if (schedule != null) {
            info.append("Rute: ").append(schedule.getKotaAsal())
                .append(" → ").append(schedule.getKotaTujuan()).append("\n");
            info.append("Tanggal Berangkat: ").append(schedule.getTanggalBerangkat()).append("\n");
            info.append("Jam Berangkat: ").append(schedule.getJamBerangkat()).append("\n\n");
        }
        
        if (passenger != null) {
            info.append("Penumpang: ").append(passenger.getNama()).append("\n");
            info.append("No. KTP: ").append(passenger.getNoKTP()).append("\n");
            info.append("No. HP: ").append(passenger.getNoHP()).append("\n\n");
        }
        
        info.append("Jumlah Seat: ").append(jumlahSeat).append("\n");
        info.append("Total Harga: Rp ").append(String.format("%,.0f", totalHarga)).append("\n");
        info.append("Status Bayar: ").append(statusBayar).append("\n");
        
        return info.toString();
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", customer=" + (customer != null ? customer.getFullName() : "null") +
                ", schedule=" + (schedule != null ? schedule.getScheduleId() : "null") +
                ", passenger=" + (passenger != null ? passenger.getNama() : "null") +
                ", jumlahSeat=" + jumlahSeat +
                ", totalHarga=" + totalHarga +
                ", statusBayar='" + statusBayar + '\'' +
                ", tanggalBooking=" + tanggalBooking +
                '}';
    }
}