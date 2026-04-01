package manager;

import dao.ScheduleDAO;
import dao.BusDAO;
import model.Schedule;
import model.Bus;
import exception.ValidationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// Manager untuk business logic terkait Schedule
// Menerapkan AGGREGATION dengan ScheduleDAO dan BusDAO
// Author: Habibi Putra Rizqullah (2411531001)
public class ScheduleManager {
    
    // AGGREGATION: menggunakan DAO
    private ScheduleDAO scheduleDAO;
    private BusDAO busDAO;
    
    public ScheduleManager(ScheduleDAO scheduleDAO, BusDAO busDAO) {
        this.scheduleDAO = scheduleDAO;
        this.busDAO = busDAO;
    }
    
    // CREATE schedule baru dengan validasi lengkap
    public boolean createSchedule(String scheduleId, String busId, String kotaAsal, String kotaTujuan,
                                  LocalDate tanggalBerangkat, LocalTime jamBerangkat, 
                                  double harga, int seatTersedia) throws ValidationException {
        // Validasi input
        if (scheduleId == null || scheduleId.trim().isEmpty()) {
            throw new ValidationException("Schedule ID tidak boleh kosong!");
        }
        
        if (kotaAsal == null || kotaAsal.trim().isEmpty()) {
            throw new ValidationException("Kota asal tidak boleh kosong!");
        }
        
        if (kotaTujuan == null || kotaTujuan.trim().isEmpty()) {
            throw new ValidationException("Kota tujuan tidak boleh kosong!");
        }
        
        if (kotaAsal.equalsIgnoreCase(kotaTujuan)) {
            throw new ValidationException("Kota asal dan tujuan tidak boleh sama!");
        }
        
        if (tanggalBerangkat == null || tanggalBerangkat.isBefore(LocalDate.now())) {
            throw new ValidationException("Tanggal berangkat tidak valid!");
        }
        
        if (jamBerangkat == null) {
            throw new ValidationException("Jam berangkat tidak boleh kosong!");
        }
        
        if (harga <= 0) {
            throw new ValidationException("Harga harus lebih dari 0!");
        }
        
        if (seatTersedia <= 0) {
            throw new ValidationException("Jumlah seat harus lebih dari 0!");
        }
        
        // Cek apakah bus ada
        Bus bus = busDAO.findById(busId);
        if (bus == null) {
            throw new ValidationException("Bus dengan ID " + busId + " tidak ditemukan!");
        }
        
        if (!bus.isAktif()) {
            throw new ValidationException("Bus sedang tidak aktif!");
        }
        
        // Validasi seat tidak melebihi kapasitas bus
        if (seatTersedia > bus.getKapasitas()) {
            throw new ValidationException("Seat tersedia melebihi kapasitas bus (" + bus.getKapasitas() + ")!");
        }
        
        // Cek duplikasi schedule ID
        if (scheduleDAO.findById(scheduleId) != null) {
            throw new ValidationException("Schedule ID sudah digunakan!");
        }
        
        // Buat Schedule object
        Schedule schedule = new Schedule(
            scheduleId, bus, kotaAsal, kotaTujuan, 
            tanggalBerangkat, jamBerangkat, harga, seatTersedia, "AKTIF"
        );
        
        return scheduleDAO.save(schedule);
    }
    
    public List<Schedule> getAllSchedules() {
        return scheduleDAO.findAll();
    }
    
    public List<Schedule> getActiveSchedules() {
        return scheduleDAO.findAllActive();
    }
    
    public Schedule getScheduleById(String scheduleId) {
        return scheduleDAO.findById(scheduleId);
    }
    
    public List<Schedule> searchScheduleByRoute(String kotaAsal, String kotaTujuan) {
        return scheduleDAO.searchByRoute(kotaAsal, kotaTujuan);
    }
    
    public List<Schedule> getSchedulesByDate(LocalDate tanggal) {
        return scheduleDAO.findByDate(tanggal);
    }
    
    // UPDATE schedule dengan validasi
    public boolean updateSchedule(Schedule schedule) throws ValidationException {
        if (schedule == null) {
            throw new ValidationException("Schedule tidak boleh null!");
        }
        
        Schedule existing = scheduleDAO.findById(schedule.getScheduleId());
        if (existing == null) {
            throw new ValidationException("Schedule tidak ditemukan!");
        }
        
        if (schedule.getHarga() <= 0) {
            throw new ValidationException("Harga harus lebih dari 0!");
        }
        
        if (schedule.getSeatTersedia() < 0) {
            throw new ValidationException("Seat tersedia tidak boleh negatif!");
        }
        
        return scheduleDAO.update(schedule);
    }
    
    // DELETE schedule dengan validasi
    public boolean deleteSchedule(String scheduleId) throws ValidationException {
        if (scheduleId == null || scheduleId.trim().isEmpty()) {
            throw new ValidationException("Schedule ID tidak boleh kosong!");
        }
        
        Schedule schedule = scheduleDAO.findById(scheduleId);
        if (schedule == null) {
            throw new ValidationException("Schedule tidak ditemukan!");
        }
        
        return scheduleDAO.delete(scheduleId);
    }
    
    // Update seat setelah booking
    public boolean bookSeats(String scheduleId, int seatCount) throws ValidationException {
        Schedule schedule = scheduleDAO.findById(scheduleId);
        
        if (schedule == null) {
            throw new ValidationException("Schedule tidak ditemukan!");
        }
        
        if (!schedule.isSeatAvailable(seatCount)) {
            throw new ValidationException("Seat tidak mencukupi!");
        }
        
        return scheduleDAO.updateSeatAvailability(scheduleId, seatCount);
    }
    
    // Generate schedule ID format SCH001
    public String generateScheduleId() {
        List<Schedule> allSchedules = scheduleDAO.findAll();
        int nextId = allSchedules.size() + 1;
        return String.format("SCH%03d", nextId);
    }
}