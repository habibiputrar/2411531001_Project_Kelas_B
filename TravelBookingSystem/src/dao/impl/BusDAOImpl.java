package dao.impl;

import dao.BusDAO;
import model.Bus;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementasi BusDAO dengan JDBC MySQL
// Menerapkan POLYMORPHISM melalui interface
// Author: Habibi Putra Rizqullah (2411531001)
public class BusDAOImpl implements BusDAO {
    
    @Override
    public boolean save(Bus bus) {
        String sql = "INSERT INTO buses (bus_id, plat_nomor, tipe_bus, kapasitas, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bus.getBusId());
            pstmt.setString(2, bus.getPlatNomor());
            pstmt.setString(3, bus.getTipeBus());
            pstmt.setInt(4, bus.getKapasitas());
            pstmt.setString(5, bus.getStatus());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving bus: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Bus findById(String busId) {
        String sql = "SELECT * FROM buses WHERE bus_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, busId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractBusFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding bus by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public Bus findByPlatNomor(String platNomor) {
        String sql = "SELECT * FROM buses WHERE plat_nomor = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, platNomor);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractBusFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding bus by plat nomor: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<Bus> findAll() {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM buses ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                buses.add(extractBusFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding all buses: " + e.getMessage());
            e.printStackTrace();
        }
        
        return buses;
    }
    
    @Override
    public List<Bus> findAllActive() {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM buses WHERE status = 'AKTIF' ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                buses.add(extractBusFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding active buses: " + e.getMessage());
            e.printStackTrace();
        }
        
        return buses;
    }
    
    @Override
    public boolean update(Bus bus) {
        String sql = "UPDATE buses SET plat_nomor = ?, tipe_bus = ?, kapasitas = ?, status = ? WHERE bus_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, bus.getPlatNomor());
            pstmt.setString(2, bus.getTipeBus());
            pstmt.setInt(3, bus.getKapasitas());
            pstmt.setString(4, bus.getStatus());
            pstmt.setString(5, bus.getBusId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating bus: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(String busId) {
        String sql = "DELETE FROM buses WHERE bus_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, busId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting bus: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Helper untuk mapping ResultSet ke Bus object
    private Bus extractBusFromResultSet(ResultSet rs) throws SQLException {
        return new Bus(
            rs.getString("bus_id"),
            rs.getString("plat_nomor"),
            rs.getString("tipe_bus"),
            rs.getInt("kapasitas"),
            rs.getString("status")
        );
    }
}