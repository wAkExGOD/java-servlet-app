package org.example.demo.dao;

import org.example.demo.model.UserAvatar;
import org.example.demo.service.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserAvatarDao {

    public void save(UserAvatar avatar) {
        String sql = "INSERT INTO user_avatars (user_id, file_name, file_path, file_type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, avatar.getUserId());
            pstmt.setString(2, avatar.getFileName());
            pstmt.setString(3, avatar.getFilePath());
            pstmt.setString(4, avatar.getFileType());

            pstmt.executeUpdate();
            System.out.println("Avatar saved successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserAvatar findByUserId(Long userId) {
        String sql = "SELECT * FROM user_avatars WHERE user_id = ? ORDER BY upload_date DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UserAvatar avatar = new UserAvatar();
                avatar.setId(rs.getLong("id"));
                avatar.setUserId(rs.getLong("user_id"));
                avatar.setFileName(rs.getString("file_name"));
                avatar.setFilePath(rs.getString("file_path"));
                avatar.setFileType(rs.getString("file_type"));
                avatar.setUploadDate(rs.getTimestamp("upload_date"));
                return avatar;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserAvatar> findAllByUserId(Long userId) {
        List<UserAvatar> avatars = new ArrayList<>();
        String sql = "SELECT * FROM user_avatars WHERE user_id = ? ORDER BY upload_date DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UserAvatar avatar = new UserAvatar();
                avatar.setId(rs.getLong("id"));
                avatar.setUserId(rs.getLong("user_id"));
                avatar.setFileName(rs.getString("file_name"));
                avatar.setFilePath(rs.getString("file_path"));
                avatar.setFileType(rs.getString("file_type"));
                avatar.setUploadDate(rs.getTimestamp("upload_date"));
                avatars.add(avatar);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avatars;
    }

    public void delete(Long id) {
        String sql = "DELETE FROM user_avatars WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            System.out.println("Avatar deleted successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}