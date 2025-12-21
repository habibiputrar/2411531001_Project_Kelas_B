package dao;

import model.Bus;
import java.util.List;

// Interface DAO untuk operasi data Bus
public interface BusDAO {

    // Simpan data bus
    boolean save(Bus bus);

    // Cari bus berdasarkan ID
    Bus findById(String busId);

    // Cari bus berdasarkan plat nomor
    Bus findByPlatNomor(String platNomor);

    // Ambil semua data bus
    List<Bus> findAll();

    // Ambil data bus yang masih aktif
    List<Bus> findAllActive();

    // Update data bus
    boolean update(Bus bus);

    // Hapus data bus berdasarkan ID
    boolean delete(String busId);
}
