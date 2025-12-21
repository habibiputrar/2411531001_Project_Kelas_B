package dao;

import model.Schedule;
import java.time.LocalDate;
import java.util.List;

// Interface DAO untuk operasi data Schedule
public interface ScheduleDAO {

    // Simpan data schedule
    boolean save(Schedule schedule);

    // Cari schedule berdasarkan ID
    Schedule findById(String scheduleId);

    // Ambil semua data schedule
    List<Schedule> findAll();

    // Ambil schedule yang masih aktif
    List<Schedule> findAllActive();

    // Cari schedule berdasarkan rute kota asal dan tujuan
    List<Schedule> searchByRoute(String kotaAsal, String kotaTujuan);

    // Cari schedule berdasarkan tanggal
    List<Schedule> findByDate(LocalDate tanggal);

    // Update data schedule
    boolean update(Schedule schedule);

    // Hapus data schedule berdasarkan ID
    boolean delete(String scheduleId);

    // Update jumlah seat tersedia setelah booking
    boolean updateSeatAvailability(String scheduleId, int seatBooked);
}
