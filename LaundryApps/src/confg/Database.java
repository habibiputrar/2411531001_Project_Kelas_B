package confg;

import java.sql.*;
import javax.swing.JOptionPane;

public class Database {
    Connection conn;

    public static Connection koneksi() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
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
}
