package util;

import java.sql.Connection;

public class TestKoneksi {
    public static void main(String[] args) {
        try {
            // Panggil method dari class DatabaseConnection
            Connection conn = DatabaseConnection.getConnection();
            
            if (conn != null) {
                System.out.println("✓ Koneksi BERHASIL!");
            } else {
                System.out.println("✗ Koneksi GAGAL!");
            }
            
        } catch (Exception e) {
            System.out.println("✗ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}