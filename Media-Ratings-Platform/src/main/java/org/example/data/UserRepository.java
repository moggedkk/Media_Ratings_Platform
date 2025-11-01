package org.example.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public String getUserId(String userToken){
        String sql = "SELECT user_id FROM users WHERE token = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userToken);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("user_id");
                }
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
