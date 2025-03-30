<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form action="signup" method="post">
    <input type="email" name="email" placeholder="Эл. почта" required />
    <input type="text" name="login" placeholder="Логин" required />
    <input type="password" name="password" placeholder="Пароль" minlength="6" required />
    <button type="submit">Зарегистрироваться</button>
    <% if (request.getAttribute("error") != null) { %>
    <p style="color: red;"><%= request.getAttribute("error") %></p>
    <% } %>
</form>


