package DAO;

import confg.Database;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepo implements UserDAO {

    private final Connection connection;
    private static final String INSERT = "INSERT INTO user (name, username, password) VALUES (?, ?, ?)";
    private static final String SELECT = "SELECT * FROM user";
    private static final String UPDATE = "UPDATE user SET name = ?, username = ?, password = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM user WHERE id = ?";

    public UserRepo() {
        connection = Database.koneksi();
    }

    @Override
    public void save(User user) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(INSERT);
            st.setString(1, user.getNama());
            st.setString(2, user.getUsername());
            st.setString(3, user.getPassword());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> show() {
        List<User> ls = null;
        try {
            ls = new ArrayList<>();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(SELECT);
            while (rs.next()) {
                User user = new User(null, null);
                user.setId(rs.getString("id"));
                user.setNama(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                ls.add(user);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return ls;
    }

    @Override
    public void update(User user) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(UPDATE);
            st.setString(1, user.getNama());
            st.setString(2, user.getUsername());
            st.setString(3, user.getPassword());
            st.setString(4, user.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(String id) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(DELETE);
            st.setString(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isUsernameExist(String username) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPasswordExist(String password) {
        String query = "SELECT COUNT(*) FROM user WHERE password = ?";
        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setString(1, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameExist(String username, String excludeId) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ? AND id != ?";
        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setString(1, username);
            st.setString(2, excludeId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPasswordExist(String password, String excludeId) {
        String query = "SELECT COUNT(*) FROM user WHERE password = ? AND id != ?";
        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setString(1, password);
            st.setString(2, excludeId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
