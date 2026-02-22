package dao;

import model.Booking;
import java.util.List;

// Interface DAO untuk operasi data Booking
public interface BookingDAO {

    // Simpan data booking
    boolean save(Booking booking);

    // Cari booking berdasarkan ID
    Booking findById(String bookingId);

    // Ambil semua data booking
    List<Booking> findAll();

    // Cari booking berdasarkan ID customer
    List<Booking> findByCustomerId(String customerId);

    // Cari booking berdasarkan ID jadwal
    List<Booking> findByScheduleId(String scheduleId);

    // Update data booking
    boolean update(Booking booking);

    // Hapus booking berdasarkan ID
    boolean delete(String bookingId);

    // Update status pembayaran booking
    boolean updatePaymentStatus(String bookingId, String statusBayar);
}
