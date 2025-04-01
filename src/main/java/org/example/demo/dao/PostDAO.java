package org.example.demo.dao;

import org.example.demo.service.DBConnection;
import org.example.demo.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {
    public void createPost(Post post) throws SQLException {
        String sql = "INSERT INTO post (title, content, author_id) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setInt(3, post.getAuthorId());
            stmt.executeUpdate();
        }
    }

    public Post getPostById(int postId) throws SQLException {
        String sql = "SELECT * FROM post WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return parsePostFromDB(rs);
                }
            }
        }
        return null;
    }

    public List<Post> getAllPosts() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM post";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                posts.add(parsePostFromDB(rs));
            }
        }
        return posts;
    }

    public void updatePost(Post post) throws SQLException {
        String sql = "UPDATE post SET title = ?, content = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setInt(3, post.getId());
            stmt.executeUpdate();
        }
    }

    public void deletePost(int postId) throws SQLException {
        String sql = "DELETE FROM post WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            stmt.executeUpdate();
        }
    }

    private Post parsePostFromDB(ResultSet rs) throws SQLException {
        return new Post(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getInt("author_id"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }
}