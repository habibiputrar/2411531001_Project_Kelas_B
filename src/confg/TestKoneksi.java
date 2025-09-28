package confg;

import java.sql.Connection;

public class TestKoneksi {
    public static void main(String[] args) {
        Connection conn = confg.Database.koneksi();
        
        if (conn != null) {
            System.out.println("Koneksi ke database BERHASIL!");
        } else {
            System.out.println("Koneksi ke database GAGAL!");
        }
    }
}
