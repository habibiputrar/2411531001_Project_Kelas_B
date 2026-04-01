package DAO;

import confg.Database;
import model.Costumer;
import model.CostumerBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CostumerRepo implements CostumerDAO {

    private Connection connection;

    private final String insert =
        "INSERT INTO costumer (nama, email, alamat, hp) VALUES (?,?,?,?)";

    private final String select =
        "SELECT * FROM costumer";

    private final String delete =
        "DELETE FROM costumer WHERE id=?";

    private final String update =
        "UPDATE costumer SET nama=?, email=?, alamat=?, hp=? WHERE id=?";

    public CostumerRepo() {
        connection = Database.koneksi();
    }

    @Override
    public void save(Costumer c) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(insert);
            st.setString(1, c.getNama());
            st.setString(2, c.getEmail());
            st.setString(3, c.getAlamat());
            st.setString(4, c.getHp());
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CostumerRepo.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try { if (st != null) st.close(); } catch (SQLException e) {}
        }
    }

    @Override
    public List<Costumer> show() {
        List<Costumer> list = null;
        try {
            list = new ArrayList<>();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);

            while (rs.next()) {
                Costumer cs = new CostumerBuilder()
                    .setId(rs.getString("id"))
                    .setNama(rs.getString("nama"))
                    .setEmail(rs.getString("email"))
                    .setAlamat(rs.getString("alamat"))
                    .setHp(rs.getString("hp"))
                    .build();

                list.add(cs);
            }

        } catch (SQLException e) {
            Logger.getLogger(CostumerRepo.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    @Override
    public void update(Costumer c) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(update);
            st.setString(1, c.getNama());
            st.setString(2, c.getEmail());
            st.setString(3, c.getAlamat());
            st.setString(4, c.getHp());
            st.setString(5, c.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CostumerRepo.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try { if (st != null) st.close(); } catch (SQLException e) {}
        }
    }

    @Override
    public void delete(String id) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(delete);
            st.setString(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(CostumerRepo.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try { if (st != null) st.close(); } catch (SQLException e) {}
        }
    }
}
