package org.example.data;

import org.example.models.Media;
import org.example.models.Genre;
import org.example.models.mediaType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MediaRepository {
    public String createMedia(Media media){
        String media_id = "-1";
        String sql = "INSERT INTO media_entries (user_id, title, description, media_type, release_year, age_restriction) VALUES (?, ?, ?,?,?,?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, media.getUser_id());
            stmt.setString(2, media.getTitle());
            stmt.setString(3, media.getDescription());
            stmt.setString(4, media.getMediaType().name());
            stmt.setInt(5, media.getReleaseYear());
            stmt.setInt(6, media.getAgeRestriction());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    media_id = keys.getString(1);  // return the new media_id
                }
            }
            return media_id;
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return media_id;
        }
    }
    public boolean createGenre(String mediaId, String genreId){
        String sql = "INSERT INTO media_genre (media_id, genre_id) VALUES (?, ?)";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(mediaId));
            stmt.setInt(2, Integer.parseInt(genreId));
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }
    public Media getMedia(String mediaId){
        String sql = "SELECT m.media_id, m.user_id, m.title, m.description, m.media_type, " +
                "m.release_year, m.age_restriction, g.name AS genre_name " +
                "FROM media_entries m " +
                "LEFT JOIN media_genre mg ON m.media_id = mg.media_id " +
                "LEFT JOIN genres g ON mg.genre_id = g.genre_id " +
                "WHERE m.media_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(mediaId));
            try (ResultSet rs = pstmt.executeQuery()) {
                Media media = null;
                List<Genre> genres = new ArrayList<>();
                while (rs.next()) {
                    if (media == null) {
                        media = new Media();
                        media.setUser_id(rs.getInt("user_id"));
                        media.setTitle(rs.getString("title"));
                        media.setDescription(rs.getString("description"));
                        media.setMediaType(mediaType.valueOf(rs.getString("media_type")));
                        media.setReleaseYear(rs.getInt("release_year"));
                        media.setAgeRestriction(rs.getInt("age_restriction"));
                    }
                    String genre = rs.getString("genre_name");
                    if (genre != null) {
                        genres.add(Genre.valueOf(genre));
                    }
                }
                if (media != null) {
                    media.setGenres(genres);
                }
                return media;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error fetching media for id " + mediaId, e);
        }
    }
    public String getUserId(String mediaId){
        String sql = "SELECT user_id FROM media_entries WHERE media_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(mediaId));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("user_id");
                }
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error fetching user_id for media entry " + mediaId, e);
        }
    }
    public String getGenreId(String genre){
        String sql = "SELECT genre_id FROM genres WHERE name = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, genre);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("genre_id");
                }
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error fetching genre id for genre " + genre, e);
        }
    }
    public boolean deleteMedia(String mediaId){
        String sql = "Delete FROM media_entries WHERE media_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(mediaId));
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error deleting media id " + mediaId, e);
        }
    }
    public  boolean deleteGenre(String mediaId){
        String sql = "DELETE FROM media_genre WHERE media_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Integer.parseInt(mediaId));
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error deleting media id " + mediaId, e);
        }
    }
    public boolean updateMedia(Media media, String mediaId){
        String updateSql = "UPDATE media_entries SET " +
                "title = ?, " +
                "description = ?, " +
                "media_type = ?, " +
                "release_year = ?, " +
                "age_restriction = ? " +
                "WHERE media_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, media.getTitle());
            pstmt.setString(2, media.getDescription());
            pstmt.setString(3, media.getMediaType().name()); // Enum to string
            pstmt.setInt(4, media.getReleaseYear());
            pstmt.setInt(5, media.getAgeRestriction());
            pstmt.setInt(6, Integer.parseInt(mediaId));
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating media ID " + mediaId, e);
        }
    }
}
