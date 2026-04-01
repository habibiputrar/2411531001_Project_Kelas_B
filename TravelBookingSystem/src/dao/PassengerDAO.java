package dao;

import model.Passenger;
import java.util.List;

// Interface DAO untuk operasi data Passenger
public interface PassengerDAO {

    // Simpan data passenger
    boolean save(Passenger passenger);

    // Cari passenger berdasarkan ID
    Passenger findById(String passengerId);

    // Cari passenger berdasarkan nomor KTP
    Passenger findByNoKTP(String noKTP);

    // Ambil semua data passenger
    List<Passenger> findAll();

    // Update data passenger
    boolean update(Passenger passenger);

    // Hapus data passenger berdasarkan ID
    boolean delete(String passengerId);
}
