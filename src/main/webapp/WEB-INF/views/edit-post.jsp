<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.demo.model.User" %>
<%@ page import="org.example.demo.model.Post" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }

    // Получаем пост из атрибутов запроса
    Post post = (Post) request.getAttribute("post");
    if (post == null) {
        response.sendRedirect("posts"); // Перенаправление, если пост не найден
        return;
    }
%>

<div class="edit-post-container">
    <h2>Редактировать пост</h2>
    <form action="${pageContext.request.contextPath}/posts/edit/<%= post.getId() %>" method="post">
        <div class="form-group">
            <label for="title">Заголовок:</label>
            <input type="text" id="title" name="title" class="input-field" value="<%= post.getTitle() %>" required>
        </div>
        <div class="form-group">
            <label for="content">Содержимое:</label>
            <textarea id="content" name="content" class="input-field" required><%= post.getContent() %></textarea>
        </div>
        <input type="hidden" name="authorId" value="<%= post.getAuthorId() %>">
        <button type="submit" class="btn btn-primary">Сохранить изменения</button>
    </form>
    <a href="${pageContext.request.contextPath}/posts" class="btn btn-secondary">Отмена</a>
</div>