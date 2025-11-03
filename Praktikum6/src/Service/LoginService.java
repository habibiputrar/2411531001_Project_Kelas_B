package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Config.Database;
import Model.User;

public class LoginService {

    public boolean authenticate(User user) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (Connection conn = Database.koneksi();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            ResultSet resultSet = statement.executeQuery();

            // Returns true if a matching record is found
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
