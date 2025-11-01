package org.example.data;

import org.example.models.Media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MediaRepository {
    public boolean createMedia(Media media){
        String sql = "INSERT INTO media (user_id, title, description, media_type, release_year, age_restriction, genre_id) VALUES (?, ?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, media.getUser_id());
            stmt.setString(2, media.getTitle());
            stmt.setString(3, media.getDescription());
            stmt.setString(4, media.getMediaType().name());
            stmt.setString(5, media.getReleaseYear().toString());
            stmt.setString(6, Integer.toString(media.getAgeRestriction()));
            //stmt.setString(7, );
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public boolean createGenre(String media_id, String genre_id){
        String sql = "INSERT INTO media_genre (media_id, genre_id) VALUES (?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, media_id);
            stmt.setString(2, genre_id);
            stmt.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
    public String getGenreMediaId(String media_id){
        String sql = "SELECT media_genre_id FROM media_genre WHERE media_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, media_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("media_genre_id");
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching media_genre_id for media entry " + media_id, e);
        }
    }
}
