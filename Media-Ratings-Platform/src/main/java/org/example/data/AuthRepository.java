package org.example.data;
import org.example.models.User;
import java.sql.*;
public class AuthRepository {
    public  boolean createUser(User user, String user_token) {
       String sql = "INSERT INTO users (username, password_hash, token) VALUES (?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());
            stmt.setString(3, user_token);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String getToken(String username) {
        String sql = "SELECT token FROM users WHERE username = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("token");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching password hash for user " + username, e);
        }
    }

    public String getHashedPassword(String username) {
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password_hash");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching password hash for user " + username, e);
        }
    }
}
