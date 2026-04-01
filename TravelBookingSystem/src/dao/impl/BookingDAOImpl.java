package dao.impl;

import dao.BookingDAO;
import dao.ScheduleDAO;
import dao.PassengerDAO;
import dao.UserDAO;
import model.Booking;
import model.Schedule;
import model.Passenger;
import model.Customer;
import model.User;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Implementasi BookingDAO dengan JDBC MySQL
// Menerapkan POLYMORPHISM dan AGGREGATION dengan DAO lain
// Author: Habibi Putra Rizqullah (2411531001)
public class BookingDAOImpl implements BookingDAO {
    
    // AGGREGATION: menggunakan DAO lain sebagai dependency
    private ScheduleDAO scheduleDAO;
    private PassengerDAO passengerDAO;
    private UserDAO userDAO;
    
    public BookingDAOImpl(ScheduleDAO scheduleDAO, PassengerDAO passengerDAO, UserDAO userDAO) {
        this.scheduleDAO = scheduleDAO;
        this.passengerDAO = passengerDAO;
        this.userDAO = userDAO;
    }
    
    @Override
    public boolean save(Booking booking) {
        String sql = "INSERT INTO bookings (booking_id, customer_id, schedule_id, passenger_id, jumlah_seat, total_harga, status_bayar) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, booking.getBookingId());
            pstmt.setString(2, booking.getCustomer().getUserId());
            pstmt.setString(3, booking.getSchedule().getScheduleId());
            pstmt.setString(4, booking.getPassenger().getPassengerId());
            pstmt.setInt(5, booking.getJumlahSeat());
            pstmt.setDouble(6, booking.getTotalHarga());
            pstmt.setString(7, booking.getStatusBayar());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Booking findById(String bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractBookingFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding booking by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY tanggal_booking DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                bookings.add(extractBookingFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding all bookings: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    @Override
    public List<Booking> findByCustomerId(String customerId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE customer_id = ? ORDER BY tanggal_booking DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                bookings.add(extractBookingFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding bookings by customer ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    @Override
    public List<Booking> findByScheduleId(String scheduleId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE schedule_id = ? ORDER BY tanggal_booking DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, scheduleId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                bookings.add(extractBookingFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding bookings by schedule ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return bookings;
    }
    
    @Override
    public boolean update(Booking booking) {
        String sql = "UPDATE bookings SET customer_id = ?, schedule_id = ?, passenger_id = ?, jumlah_seat = ?, " +
                     "total_harga = ?, status_bayar = ? WHERE booking_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, booking.getCustomer().getUserId());
            pstmt.setString(2, booking.getSchedule().getScheduleId());
            pstmt.setString(3, booking.getPassenger().getPassengerId());
            pstmt.setInt(4, booking.getJumlahSeat());
            pstmt.setDouble(5, booking.getTotalHarga());
            pstmt.setString(6, booking.getStatusBayar());
            pstmt.setString(7, booking.getBookingId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(String bookingId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bookingId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updatePaymentStatus(String bookingId, String statusBayar) {
        String sql = "UPDATE bookings SET status_bayar = ? WHERE booking_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, statusBayar);
            pstmt.setString(2, bookingId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating payment status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Helper untuk mapping ResultSet ke Booking object
    // AGGREGATION: menggunakan DAO lain untuk load relasi
    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        String bookingId = rs.getString("booking_id");
        String customerId = rs.getString("customer_id");
        String scheduleId = rs.getString("schedule_id");
        String passengerId = rs.getString("passenger_id");
        int jumlahSeat = rs.getInt("jumlah_seat");
        double totalHarga = rs.getDouble("total_harga");
        String statusBayar = rs.getString("status_bayar");
        LocalDateTime tanggalBooking = rs.getTimestamp("tanggal_booking").toLocalDateTime();
        
        // Load data terkait menggunakan DAO lain
        User user = userDAO.findById(customerId);
        Customer customer = (user instanceof Customer) ? (Customer) user : null;
        Schedule schedule = scheduleDAO.findById(scheduleId);
        Passenger passenger = passengerDAO.findById(passengerId);
        
        return new Booking(bookingId, customer, schedule, passenger, jumlahSeat, 
                          totalHarga, statusBayar, tanggalBooking);
    }
}