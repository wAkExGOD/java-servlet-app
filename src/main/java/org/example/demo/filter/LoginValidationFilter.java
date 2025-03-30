package org.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.demo.controller.BaseServlet;
import org.example.demo.controller.NumberServlet;

import java.io.IOException;

@WebFilter("/login")
public class LoginValidationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    private static final Logger logger = LogManager.getLogger(LoginValidationFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if ("POST".equalsIgnoreCase(httpRequest.getMethod()) && httpRequest.getRequestURI().endsWith("/login")) {
            String login = httpRequest.getParameter("login");
            String password = httpRequest.getParameter("password");
            String errorMessage = null;

            if (login == null || login.isEmpty()) {
                errorMessage = "Логин не может быть пустым.";
            } else if (password == null || password.length() < 6) {
                errorMessage = "Пароль должен содержать не менее 6 символов.";
            }

            if (errorMessage != null) {
                httpRequest.setAttribute("error", errorMessage);
                BaseServlet.renderPage(httpRequest, httpResponse, "Логин", "/WEB-INF/views/login.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}