package dao;

import model.User;
import exception.InvalidLoginException;
import java.util.List;

// Interface DAO untuk operasi data User (Admin & Customer)
public interface UserDAO {

    // Simpan data user
    boolean save(User user);

    // Cari user berdasarkan ID
    User findById(String userId);

    // Cari user berdasarkan username
    User findByUsername(String username);

    // Ambil semua data user
    List<User> findAll();

    // Update data user
    boolean update(User user);

    // Hapus data user berdasarkan ID
    boolean delete(String userId);

    // Autentikasi login user
    User authenticate(String username, String password) throws InvalidLoginException;
}
