package org.example.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.demo.model.Post;
import org.example.demo.service.PostService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/posts") // Обрабатываем запросы на /posts
public class PostsServlet extends BaseServlet {
    private PostService postService = new PostService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        List<Post> posts;
        int page = 1; // Начальная страница
        int pageSize = 5; // Количество постов на странице

        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        try {
            posts = postService.getAllPosts(userId, page, pageSize);

            int totalPosts = postService.getTotalPosts(userId);
            request.setAttribute("posts", posts);
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("totalPosts", totalPosts);

            renderPage(request, response, "Посты", "/WEB-INF/views/posts.jsp");
        } catch (SQLException e) {
            request.setAttribute("error", "Ошибка при получении постов");
            renderPage(request, response, "Ошибка", "/WEB-INF/views/error.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int authorId = (int) session.getAttribute("userId");

        try {
            postService.createPost(title, content, authorId);
            response.sendRedirect("posts");
        } catch (SQLException e) {
            request.setAttribute("error", "Ошибка при создании поста");
            renderPage(request, response, "Ошибка", "/WEB-INF/views/error.jsp");
        }
    }
}