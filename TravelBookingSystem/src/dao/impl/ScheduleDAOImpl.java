package dao.impl;

import dao.ScheduleDAO;
import dao.BusDAO;
import model.Schedule;
import model.Bus;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Implementasi ScheduleDAO dengan JDBC MySQL
// Menerapkan POLYMORPHISM dan AGGREGATION dengan BusDAO
// Author: Habibi Putra Rizqullah (2411531001)
public class ScheduleDAOImpl implements ScheduleDAO {
    
    // AGGREGATION: menggunakan BusDAO sebagai dependency
    private BusDAO busDAO;
    
    public ScheduleDAOImpl(BusDAO busDAO) {
        this.busDAO = busDAO;
    }
    
    @Override
    public boolean save(Schedule schedule) {
        String sql = "INSERT INTO schedules (schedule_id, bus_id, kota_asal, kota_tujuan, tanggal_berangkat, jam_berangkat, harga, seat_tersedia, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, schedule.getScheduleId());
            pstmt.setString(2, schedule.getBus().getBusId());
            pstmt.setString(3, schedule.getKotaAsal());
            pstmt.setString(4, schedule.getKotaTujuan());
            pstmt.setDate(5, Date.valueOf(schedule.getTanggalBerangkat()));
            pstmt.setTime(6, Time.valueOf(schedule.getJamBerangkat()));
            pstmt.setDouble(7, schedule.getHarga());
            pstmt.setInt(8, schedule.getSeatTersedia());
            pstmt.setString(9, schedule.getStatus());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving schedule: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Schedule findById(String scheduleId) {
        String sql = "SELECT * FROM schedules WHERE schedule_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, scheduleId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractScheduleFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding schedule by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<Schedule> findAll() {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedules ORDER BY tanggal_berangkat, jam_berangkat";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                schedules.add(extractScheduleFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding all schedules: " + e.getMessage());
            e.printStackTrace();
        }
        
        return schedules;
    }
    
    @Override
    public List<Schedule> findAllActive() {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedules WHERE status = 'AKTIF' ORDER BY tanggal_berangkat, jam_berangkat";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                schedules.add(extractScheduleFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding active schedules: " + e.getMessage());
            e.printStackTrace();
        }
        
        return schedules;
    }
    
    @Override
    public List<Schedule> searchByRoute(String kotaAsal, String kotaTujuan) {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedules WHERE kota_asal LIKE ? AND kota_tujuan LIKE ? AND status = 'AKTIF' " +
                     "ORDER BY tanggal_berangkat, jam_berangkat";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, "%" + kotaAsal + "%");
            pstmt.setString(2, "%" + kotaTujuan + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                schedules.add(extractScheduleFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error searching schedules by route: " + e.getMessage());
            e.printStackTrace();
        }
        
        return schedules;
    }
    
    @Override
    public List<Schedule> findByDate(LocalDate tanggal) {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT * FROM schedules WHERE tanggal_berangkat = ? AND status = 'AKTIF' " +
                     "ORDER BY jam_berangkat";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, Date.valueOf(tanggal));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                schedules.add(extractScheduleFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding schedules by date: " + e.getMessage());
            e.printStackTrace();
        }
        
        return schedules;
    }
    
    @Override
    public boolean update(Schedule schedule) {
        String sql = "UPDATE schedules SET bus_id = ?, kota_asal = ?, kota_tujuan = ?, tanggal_berangkat = ?, " +
                     "jam_berangkat = ?, harga = ?, seat_tersedia = ?, status = ? WHERE schedule_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, schedule.getBus().getBusId());
            pstmt.setString(2, schedule.getKotaAsal());
            pstmt.setString(3, schedule.getKotaTujuan());
            pstmt.setDate(4, Date.valueOf(schedule.getTanggalBerangkat()));
            pstmt.setTime(5, Time.valueOf(schedule.getJamBerangkat()));
            pstmt.setDouble(6, schedule.getHarga());
            pstmt.setInt(7, schedule.getSeatTersedia());
            pstmt.setString(8, schedule.getStatus());
            pstmt.setString(9, schedule.getScheduleId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating schedule: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(String scheduleId) {
        String sql = "DELETE FROM schedules WHERE schedule_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, scheduleId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting schedule: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updateSeatAvailability(String scheduleId, int seatBooked) {
        String sql = "UPDATE schedules SET seat_tersedia = seat_tersedia - ? WHERE schedule_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, seatBooked);
            pstmt.setString(2, scheduleId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating seat availability: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Helper untuk mapping ResultSet ke Schedule object
    // AGGREGATION: menggunakan BusDAO untuk load data Bus
    private Schedule extractScheduleFromResultSet(ResultSet rs) throws SQLException {
        String scheduleId = rs.getString("schedule_id");
        String busId = rs.getString("bus_id");
        String kotaAsal = rs.getString("kota_asal");
        String kotaTujuan = rs.getString("kota_tujuan");
        LocalDate tanggalBerangkat = rs.getDate("tanggal_berangkat").toLocalDate();
        LocalTime jamBerangkat = rs.getTime("jam_berangkat").toLocalTime();
        double harga = rs.getDouble("harga");
        int seatTersedia = rs.getInt("seat_tersedia");
        String status = rs.getString("status");
        
        // Load data Bus menggunakan BusDAO
        Bus bus = busDAO.findById(busId);
        
        return new Schedule(scheduleId, bus, kotaAsal, kotaTujuan, tanggalBerangkat, 
                           jamBerangkat, harga, seatTersedia, status);
    }
}