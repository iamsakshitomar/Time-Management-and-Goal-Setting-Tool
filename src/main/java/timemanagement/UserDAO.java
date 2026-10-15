package timemanagement;

import java.sql.*;

public class UserDAO {

    public int register(String name, String email, String password) throws SQLException {
        String sql = "INSERT INTO users(name,email,password_hash) VALUES(?,?,?)";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, PasswordUtil.sha256(password));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    public Integer login(String email, String password) throws SQLException {
        String sql = "SELECT id FROM users WHERE email=? AND password_hash=?";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, PasswordUtil.sha256(password));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        }
        return null;
    }
}
