package org.example.demo.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.demo.dao.PostDAO;
import org.example.demo.model.Post;

import java.sql.SQLException;
import java.util.List;

public class PostService {
    private static final Logger logger = LogManager.getLogger(PostService.class);
    private PostDAO postDAO = new PostDAO();

    public void createPost(String title, String content, int authorId) throws SQLException {
        Post post = new Post(title, content, authorId);
        try {
            postDAO.createPost(post);
            logger.info("Post created successfully: {}", title);
        } catch (SQLException e) {
            logger.error("Error creating post: {}", title, e);
            throw e; // Пробрасываем исключение дальше
        }
    }

    public Post getPostById(int postId) throws SQLException {
        try {
            Post post = postDAO.getPostById(postId);
            if (post != null) {
                logger.info("Post retrieved successfully: {}", post.getTitle());
            } else {
                logger.warn("Post not found with ID: {}", postId);
            }
            return post;
        } catch (SQLException e) {
            logger.error("Error retrieving post with ID: {}", postId, e);
            throw e;
        }
    }

    public List<Post> getAllPosts(int authorId, int page, int pageSize) throws SQLException {
        try {
            int offset = (page - 1) * pageSize;
            List<Post> posts = postDAO.getAllPosts(authorId, offset, pageSize);
            logger.info("Retrieved all posts, count: {}", posts != null ? posts.size() : 0);
            return posts;
        } catch (SQLException e) {
            logger.error("Error retrieving all posts", e);
            throw e;
        }
    }

    public int getTotalPosts(int authorId) throws SQLException {
        try {
            return postDAO.getTotalPosts(authorId);
        } catch (SQLException e) {
            logger.error("Error retrieving total posts for author ID: {}", authorId, e);
            throw e;
        }
    }

    public void updatePost(int postId, String title, String content, int authorId) throws SQLException {
        Post post = new Post(postId, title, content, authorId, null, null);
        try {
            postDAO.updatePost(post);
            logger.info("Post updated successfully: {}", title);
        } catch (SQLException e) {
            logger.error("Error updating post: {}", title, e);
            throw e;
        }
    }

    public void deletePost(int postId) throws SQLException {
        try {
            postDAO.deletePost(postId);
            logger.info("Post deleted successfully with ID: {}", postId);
        } catch (SQLException e) {
            logger.error("Error deleting post with ID: {}", postId, e);
            throw e;
        }
    }
}