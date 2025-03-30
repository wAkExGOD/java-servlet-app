package org.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.controller.BaseServlet;

import java.io.IOException;

@WebFilter("/signup")
public class SignUpValidationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if ("POST".equalsIgnoreCase(httpRequest.getMethod()) && httpRequest.getRequestURI().endsWith("/signup")) {
            String login = httpRequest.getParameter("login");
            String password = httpRequest.getParameter("password");
            String email = httpRequest.getParameter("email");
            String errorMessage = null;

            if (login == null || login.isEmpty()) {
                errorMessage = "Логин не может быть пустым.";
            } else if (password == null || password.length() < 6) {
                errorMessage = "Пароль должен содержать не менее 6 символов.";
            } else if (email == null || !email.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
                errorMessage = "Некорректный адрес электронной почты.";
            }

            if (errorMessage != null) {
                 httpRequest.setAttribute("error", errorMessage);
                // httpRequest.getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(httpRequest, httpResponse);
                BaseServlet.renderPage(httpRequest, httpResponse, "Регистрация", "/WEB-INF/views/signup.jsp");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}