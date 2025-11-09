package DAO;

import confg.Database;
import model.Costumer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CostumerRepo implements CostumerDAO {

    private final Connection connection;
    private static final String INSERT = "INSERT INTO costumer (id, nama, alamat, nomor_hp) VALUES (?,?,?,?)";
    private static final String SELECT = "SELECT * FROM costumer";
    private static final String UPDATE = "UPDATE costumer SET nama=?, alamat=?, nomor_hp=? WHERE id=?";
    private static final String DELETE = "DELETE FROM costumer WHERE id=?";

    public CostumerRepo() {
        connection = Database.koneksi();
    }

    @Override
    public void save(Costumer costumer) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(INSERT);
            st.setString(1, costumer.getId());
            st.setString(2, costumer.getNama());
            st.setString(3, costumer.getAlamat());
            st.setString(4, costumer.getNomorHp());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Costumer> show() {
        List<Costumer> list = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(SELECT);
            while (rs.next()) {
                Costumer costumer = new Costumer();
                costumer.setId(rs.getString("id"));
                costumer.setNama(rs.getString("nama"));
                costumer.setAlamat(rs.getString("alamat"));
                costumer.setNomorHp(rs.getString("nomor_hp"));
                list.add(costumer);
            }
        } catch (SQLException e) {
            Logger.getLogger(CostumerRepo.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    @Override
    public void update(Costumer costumer) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(UPDATE);
            st.setString(1, costumer.getNama());
            st.setString(2, costumer.getAlamat());
            st.setString(3, costumer.getNomorHp());
            st.setString(4, costumer.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
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
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
