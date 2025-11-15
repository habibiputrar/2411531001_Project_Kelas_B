package confg;

import java.sql.*;
import javax.swing.JOptionPane;

public class Database {
    private static Connection conn;

    public static Connection koneksi() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost/laundry_apps", 
                    "root", 
                    ""
                );
                return conn;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                return null;
            }
        }
        return conn;
    }
}
