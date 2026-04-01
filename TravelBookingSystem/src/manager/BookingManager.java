package manager;

import dao.BookingDAO;
import dao.ScheduleDAO;
import dao.PassengerDAO;
import model.Booking;
import model.Customer;
import model.Schedule;
import model.Passenger;
import exception.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

// Manager untuk business logic terkait Booking
// Menerapkan AGGREGATION dengan multiple DAO dan BUILDER PATTERN
// Author: Habibi Putra Rizqullah (2411531001)
public class BookingManager {
    
    // AGGREGATION: menggunakan multiple DAO
    private BookingDAO bookingDAO;
    private ScheduleDAO scheduleDAO;
    private PassengerDAO passengerDAO;
    
    public BookingManager(BookingDAO bookingDAO, ScheduleDAO scheduleDAO, PassengerDAO passengerDAO) {
        this.bookingDAO = bookingDAO;
        this.scheduleDAO = scheduleDAO;
        this.passengerDAO = passengerDAO;
    }
    
    // CREATE booking baru menggunakan BUILDER PATTERN
    public Booking createBooking(Customer customer, Schedule schedule, 
                                String passengerNama, String passengerKTP, String passengerHP,
                                int jumlahSeat) throws ValidationException {
        
        // Validasi input dasar
        if (customer == null) {
            throw new ValidationException("Customer tidak boleh kosong!");
        }
        
        if (schedule == null) {
            throw new ValidationException("Schedule tidak boleh dipilih!");
        }
        
        if (!schedule.isAktif()) {
            throw new ValidationException("Schedule tidak aktif!");
        }
        
        if (jumlahSeat <= 0) {
            throw new ValidationException("Jumlah seat harus lebih dari 0!");
        }
        
        if (!schedule.isSeatAvailable(jumlahSeat)) {
            throw new ValidationException("Seat tidak tersedia! Tersedia: " + schedule.getSeatTersedia());
        }
        
        // Validasi data penumpang
        if (passengerNama == null || passengerNama.trim().isEmpty()) {
            throw new ValidationException("Nama penumpang tidak boleh kosong!");
        }
        
        if (passengerKTP == null || !passengerKTP.matches("\\d{16}")) {
            throw new ValidationException("Nomor KTP tidak valid! Harus 16 digit angka.");
        }
        
        if (passengerHP == null || !passengerHP.matches("\\d{10,13}")) {
            throw new ValidationException("Nomor HP tidak valid! Harus 10-13 digit angka.");
        }
        
        // Cek apakah passenger sudah ada berdasarkan KTP
        Passenger passenger = passengerDAO.findByNoKTP(passengerKTP);
        
        if (passenger == null) {
            // Buat passenger baru
            String passengerId = generatePassengerId();
            passenger = new Passenger(passengerId, passengerNama, passengerKTP, passengerHP);
            
            boolean savedPassenger = passengerDAO.save(passenger);
            if (!savedPassenger) {
                throw new ValidationException("Gagal menyimpan data penumpang!");
            }
        }
        
        // Generate booking ID
        String bookingId = generateBookingId();
        
        // Calculate total harga
        double totalHarga = schedule.getHarga() * jumlahSeat;
        
        // Gunakan BUILDER PATTERN untuk membuat booking
        BookingBuilder builder = new BookingBuilder();
        Booking booking = builder
            .setBookingId(bookingId)
            .setCustomer(customer)
            .setSchedule(schedule)
            .setPassenger(passenger)
            .setJumlahSeat(jumlahSeat)
            .setTotalHarga(totalHarga)
            .setStatusBayar("BELUM_BAYAR")
            .setTanggalBooking(LocalDateTime.now())
            .build();
        
        // Simpan booking ke database
        boolean savedBooking = bookingDAO.save(booking);
        if (!savedBooking) {
            throw new ValidationException("Gagal menyimpan booking!");
        }
        
        // Update seat tersedia di schedule
        schedule.bookSeat(jumlahSeat);
        scheduleDAO.updateSeatAvailability(schedule.getScheduleId(), jumlahSeat);
        
        return booking;
    }
    
    public List<Booking> getAllBookings() {
        return bookingDAO.findAll();
    }
    
    public Booking getBookingById(String bookingId) {
        return bookingDAO.findById(bookingId);
    }
    
    public List<Booking> getBookingsByCustomer(String customerId) {
        return bookingDAO.findByCustomerId(customerId);
    }
    
    public List<Booking> getBookingsBySchedule(String scheduleId) {
        return bookingDAO.findByScheduleId(scheduleId);
    }
    
    // Konfirmasi pembayaran booking
    public boolean confirmPayment(String bookingId) throws ValidationException {
        Booking booking = bookingDAO.findById(bookingId);
        
        if (booking == null) {
            throw new ValidationException("Booking tidak ditemukan!");
        }
        
        if (booking.isBayar()) {
            throw new ValidationException("Booking sudah lunas!");
        }
        
        return bookingDAO.updatePaymentStatus(bookingId, "LUNAS");
    }
    
    // Membatalkan booking dan kembalikan seat
    public boolean cancelBooking(String bookingId) throws ValidationException {
        Booking booking = bookingDAO.findById(bookingId);
        
        if (booking == null) {
            throw new ValidationException("Booking tidak ditemukan!");
        }
        
        if (booking.isBayar()) {
            throw new ValidationException("Booking yang sudah lunas tidak dapat dibatalkan!");
        }
        
        // Kembalikan seat ke schedule
        Schedule schedule = booking.getSchedule();
        if (schedule != null) {
            schedule.releaseSeat(booking.getJumlahSeat());
            scheduleDAO.update(schedule);
        }
        
        return bookingDAO.delete(bookingId);
    }
    
    // Calculate total revenue dari semua booking
    public double calculateTotalRevenue() {
        List<Booking> allBookings = bookingDAO.findAll();
        double total = 0;
        
        for (Booking booking : allBookings) {
            if (booking.isBayar()) {
                total += booking.getTotalHarga();
            }
        }
        
        return total;
    }
    
    public int getTotalBookingsByCustomer(String customerId) {
        return bookingDAO.findByCustomerId(customerId).size();
    }
    
    // Generate booking ID format BKG00001
    public String generateBookingId() {
        List<Booking> allBookings = bookingDAO.findAll();
        int nextId = allBookings.size() + 1;
        return String.format("BKG%05d", nextId);
    }
    
    // Generate passenger ID format PSG00001
    private String generatePassengerId() {
        List<Passenger> allPassengers = passengerDAO.findAll();
        int nextId = allPassengers.size() + 1;
        return String.format("PSG%05d", nextId);
    }
}