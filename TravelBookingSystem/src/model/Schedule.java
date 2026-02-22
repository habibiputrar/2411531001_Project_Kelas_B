package model;

import java.time.LocalDate;
import java.time.LocalTime;

// Model class untuk jadwal perjalanan
// Menerapkan ENCAPSULATION dan COMPOSITION (HAS-A relationship dengan Bus)
// Author: Habibi Putra Rizqullah (2411531001)
public class Schedule {
    // ENCAPSULATION: atribut private
    private String scheduleId;
    private Bus bus; // COMPOSITION: Schedule HAS-A Bus
    private String kotaAsal;
    private String kotaTujuan;
    private LocalDate tanggalBerangkat;
    private LocalTime jamBerangkat;
    private double harga;
    private int seatTersedia;
    private String status;
    
    public Schedule() {
    }
    
    public Schedule(String scheduleId, Bus bus, String kotaAsal, String kotaTujuan, 
                    LocalDate tanggalBerangkat, LocalTime jamBerangkat, 
                    double harga, int seatTersedia, String status) {
        this.scheduleId = scheduleId;
        this.bus = bus;
        this.kotaAsal = kotaAsal;
        this.kotaTujuan = kotaTujuan;
        this.tanggalBerangkat = tanggalBerangkat;
        this.jamBerangkat = jamBerangkat;
        this.harga = harga;
        this.seatTersedia = seatTersedia;
        this.status = status;
    }
    
    // ENCAPSULATION: getter dan setter
    public String getScheduleId() {
        return scheduleId;
    }
    
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }
    
    public Bus getBus() {
        return bus;
    }
    
    public void setBus(Bus bus) {
        this.bus = bus;
    }
    
    public String getKotaAsal() {
        return kotaAsal;
    }
    
    public void setKotaAsal(String kotaAsal) {
        this.kotaAsal = kotaAsal;
    }
    
    public String getKotaTujuan() {
        return kotaTujuan;
    }
    
    public void setKotaTujuan(String kotaTujuan) {
        this.kotaTujuan = kotaTujuan;
    }
    
    public LocalDate getTanggalBerangkat() {
        return tanggalBerangkat;
    }
    
    public void setTanggalBerangkat(LocalDate tanggalBerangkat) {
        this.tanggalBerangkat = tanggalBerangkat;
    }
    
    public LocalTime getJamBerangkat() {
        return jamBerangkat;
    }
    
    public void setJamBerangkat(LocalTime jamBerangkat) {
        this.jamBerangkat = jamBerangkat;
    }
    
    public double getHarga() {
        return harga;
    }
    
    public void setHarga(double harga) {
        this.harga = harga;
    }
    
    public int getSeatTersedia() {
        return seatTersedia;
    }
    
    public void setSeatTersedia(int seatTersedia) {
        this.seatTersedia = seatTersedia;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    // Cek apakah jadwal masih aktif
    public boolean isAktif() {
        return "AKTIF".equalsIgnoreCase(status);
    }
    
    // Cek apakah seat masih tersedia
    public boolean isSeatAvailable(int jumlahSeat) {
        return seatTersedia >= jumlahSeat;
    }
    
    // Kurangi seat saat booking
    public void bookSeat(int jumlahSeat) {
        if (isSeatAvailable(jumlahSeat)) {
            this.seatTersedia -= jumlahSeat;
        }
    }
    
    // Kembalikan seat saat cancel booking
    public void releaseSeat(int jumlahSeat) {
        this.seatTersedia += jumlahSeat;
    }
    
    // Display info schedule
    public String displayInfo() {
        return "Schedule ID: " + scheduleId + "\n" +
               "Rute: " + kotaAsal + " → " + kotaTujuan + "\n" +
               "Tanggal: " + tanggalBerangkat + "\n" +
               "Jam: " + jamBerangkat + "\n" +
               "Bus: " + (bus != null ? bus.getPlatNomor() + " (" + bus.getTipeBus() + ")" : "N/A") + "\n" +
               "Harga: Rp " + String.format("%,.0f", harga) + "\n" +
               "Seat Tersedia: " + seatTersedia + "\n" +
               "Status: " + status;
    }
    
    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId='" + scheduleId + '\'' +
                ", kotaAsal='" + kotaAsal + '\'' +
                ", kotaTujuan='" + kotaTujuan + '\'' +
                ", tanggalBerangkat=" + tanggalBerangkat +
                ", jamBerangkat=" + jamBerangkat +
                ", harga=" + harga +
                ", seatTersedia=" + seatTersedia +
                ", status='" + status + '\'' +
                '}';
    }
}