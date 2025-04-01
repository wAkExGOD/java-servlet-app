package org.example.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.model.Post;
import org.example.demo.service.PostService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/posts/edit/*")
public class PostsEditServlet extends BaseServlet {
    private PostService postService = new PostService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postIdParam = request.getPathInfo(); // Получаем ID поста из URL
        if (postIdParam != null && postIdParam.startsWith("/")) {
            int postId = Integer.parseInt(postIdParam.substring(1)); // Убираем слеш
            try {
                Post post = postService.getPostById(postId);
                if (post != null) {
                    request.setAttribute("post", post);
                    renderPage(request, response, "Редактирование поста", "/WEB-INF/views/edit-post.jsp");
                } else {
                    request.setAttribute("error", "Пост не найден");
                    renderPage(request, response, "Ошибка", "/WEB-INF/views/error.jsp");
                }
            } catch (SQLException e) {
                request.setAttribute("error", "Ошибка при получении поста");
                renderPage(request, response, "Ошибка", "/WEB-INF/views/error.jsp");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid post ID");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postIdParam = request.getPathInfo();
        if (postIdParam != null && postIdParam.startsWith("/")) {
            int postId = Integer.parseInt(postIdParam.substring(1)); // Убираем слеш

            String title = request.getParameter("title");
            String content = request.getParameter("content");
            int authorId = Integer.parseInt(request.getParameter("authorId")); // Или извлеките из сессии

            try {
                postService.updatePost(postId, title, content, authorId);
                response.sendRedirect(request.getContextPath() + "/posts");
            } catch (SQLException e) {
                request.setAttribute("error", "Ошибка при обновлении поста");
                renderPage(request, response, "Ошибка", "/WEB-INF/views/error.jsp");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid post ID");
        }
    }
}