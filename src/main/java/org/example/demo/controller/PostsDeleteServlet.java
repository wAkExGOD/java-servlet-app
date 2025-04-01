package org.example.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.service.PostService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/posts/delete/*") // Обрабатываем запросы на удаление постов
public class PostsDeleteServlet extends BaseServlet {
    private PostService postService = new PostService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postIdParam = request.getPathInfo(); // Получаем ID поста из URL
        if (postIdParam != null && postIdParam.startsWith("/")) {
            int postId = Integer.parseInt(postIdParam.substring(1)); // Убираем слеш
            try {
                postService.deletePost(postId);
                response.sendRedirect(request.getContextPath() + "/posts");
            } catch (SQLException e) {
                request.setAttribute("error", "Ошибка при удалении поста");
                renderPage(request, response, "Ошибка", "/WEB-INF/views/error.jsp");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid post ID");
        }
    }
}