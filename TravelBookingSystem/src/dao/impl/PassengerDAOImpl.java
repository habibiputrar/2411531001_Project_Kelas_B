package dao.impl;

import dao.PassengerDAO;
import model.Passenger;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementasi PassengerDAO dengan JDBC MySQL
// Menerapkan POLYMORPHISM melalui interface
// Author: Habibi Putra Rizqullah (2411531001)
public class PassengerDAOImpl implements PassengerDAO {
    
    @Override
    public boolean save(Passenger passenger) {
        String sql = "INSERT INTO passengers (passenger_id, nama, no_ktp, no_hp) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, passenger.getPassengerId());
            pstmt.setString(2, passenger.getNama());
            pstmt.setString(3, passenger.getNoKTP());
            pstmt.setString(4, passenger.getNoHP());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error saving passenger: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Passenger findById(String passengerId) {
        String sql = "SELECT * FROM passengers WHERE passenger_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, passengerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractPassengerFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding passenger by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public Passenger findByNoKTP(String noKTP) {
        String sql = "SELECT * FROM passengers WHERE no_ktp = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, noKTP);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractPassengerFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding passenger by KTP: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<Passenger> findAll() {
        List<Passenger> passengers = new ArrayList<>();
        String sql = "SELECT * FROM passengers ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                passengers.add(extractPassengerFromResultSet(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding all passengers: " + e.getMessage());
            e.printStackTrace();
        }
        
        return passengers;
    }
    
    @Override
    public boolean update(Passenger passenger) {
        String sql = "UPDATE passengers SET nama = ?, no_ktp = ?, no_hp = ? WHERE passenger_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, passenger.getNama());
            pstmt.setString(2, passenger.getNoKTP());
            pstmt.setString(3, passenger.getNoHP());
            pstmt.setString(4, passenger.getPassengerId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating passenger: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(String passengerId) {
        String sql = "DELETE FROM passengers WHERE passenger_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, passengerId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting passenger: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Helper untuk mapping ResultSet ke Passenger object
    private Passenger extractPassengerFromResultSet(ResultSet rs) throws SQLException {
        return new Passenger(
            rs.getString("passenger_id"),
            rs.getString("nama"),
            rs.getString("no_ktp"),
            rs.getString("no_hp")
        );
    }
}