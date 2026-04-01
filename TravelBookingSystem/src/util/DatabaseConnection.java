package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Utility class untuk koneksi ke MySQL database
// Menerapkan ENCAPSULATION
// Author: Habibi Putra Rizqullah (2411531001)
public class DatabaseConnection {
    // ENCAPSULATION: atribut private untuk konfigurasi database
    private static final String URL = "jdbc:mysql://localhost:3306/travel_booking_db";
    private static final String USERNAME = "root"; // Sesuaikan dengan username MySQL Anda
    private static final String PASSWORD = ""; // Sesuaikan dengan password MySQL Anda
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Get koneksi ke database
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DRIVER);

            // Establish connection
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connected successfully!");
            return conn;

        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
            throw new SQLException("Driver not found", e);
        } catch (SQLException e) {
            System.err.println("Failed to connect to database!");
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

    // Menutup koneksi database
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close connection!");
                e.printStackTrace();
            }
        }
    }

    // Test koneksi database
    public static void testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ Database connection test successful!");
                closeConnection(conn);
            }
        } catch (SQLException e) {
            System.err.println("✗ Database connection test failed!");
            e.printStackTrace();
        }
    }
}