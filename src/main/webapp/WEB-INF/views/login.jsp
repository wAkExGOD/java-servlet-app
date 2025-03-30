<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form action="login" method="post">
    <h2>Вход</h2>
    <input type="text" name="login" placeholder="Логин" >
    <input type="password" name="password" placeholder="Пароль" minlength="6" required>
    <button type="submit">Войти</button>
    <% if (request.getAttribute("error") != null) { %>
    <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>
</form>