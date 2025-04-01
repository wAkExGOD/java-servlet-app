package org.example.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.demo.service.AuthService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/confirm")
public class ConfirmEmailServlet extends BaseServlet {
    private static final Logger logger = LogManager.getLogger(ConfirmEmailServlet.class);
    private AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        logger.info("Received confirmation token: {}", token);

        if (token == null) {
            req.setAttribute("error", "Токен подтверждения не предоставлен");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            return;
        }

        try {
            boolean confirmed = authService.confirmRegistration(token);
            if (confirmed) {
                logger.info("Email confirmed successfully for token: {}", token);
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                logger.error("Failed to confirm email for token: {}", token);
                req.setAttribute("error",
                        "Не удалось подтвердить email. Возможно, токен недействителен или уже использован.");
                req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            logger.error("Error confirming email", e);
            req.setAttribute("error", "Произошла ошибка при подтверждении email");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
