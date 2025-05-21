<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.demo.model.User" %>
<%@ page import="org.example.demo.model.Post" %>
<%@ page import="java.util.List" %>
<%
  User user = (User) session.getAttribute("user");
  if (user == null) {
    response.sendRedirect("login");
    return;
  }
%>

<div class="posts-container">
  <h2>Список постов</h2>

  <div class="new-post-form">
    <h3>Создать новый пост</h3>
    <form action="${pageContext.request.contextPath}/posts" method="post">
      <div class="form-group">
        <label for="title">Заголовок:</label>
        <input type="text" id="title" name="title" class="input-field" required>
      </div>
      <div class="form-group">
        <label for="content">Содержимое:</label>
        <textarea id="content" name="content" class="input-field" required></textarea>
      </div>
      <input type="hidden" name="authorId" value="<%= user.getId() %>">
      <button type="submit" class="btn btn-primary">Создать пост</button>
    </form>
  </div>

  <div class="posts-list">
    <h3>Посты</h3>
    <ul class="posts-ul">
      <%
        List<Post> posts = (List<Post>) request.getAttribute("posts");
        if (posts != null && !posts.isEmpty()) {
          for (Post post : posts) {
      %>
      <li class="post-item">
        <h4 class="post-title"><%= post.getTitle() %></h4>
        <p class="post-content"><%= post.getContent() %></p>
        <small class="post-meta">Автор: <%= post.getAuthorId() %>, Дата: <%= post.getCreatedAt() %></small>
        <div class="post-actions">
          <a href="${pageContext.request.contextPath}/posts/edit/<%= post.getId() %>" class="btn btn-edit">Редактировать</a>
          <form action="${pageContext.request.contextPath}/posts/delete/<%= post.getId() %>" method="post" class="delete-form">
            <input type="hidden" name="_method" value="DELETE">
            <button type="submit" class="btn btn-delete">Удалить</button>
          </form>
        </div>
      </li>
      <%
        }
      } else {
      %>
      <li class="post-item">Посты отсутствуют.</li>
      <%
        }
      %>
    </ul>
    <div class="pagination">
      <%
        int totalPosts = (Integer) request.getAttribute("totalPosts");
        int pageSize = (Integer) request.getAttribute("pageSize");
        int currentPage = (Integer) request.getAttribute("currentPage");
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);

        for (int i = 1; i <= totalPages; i++) {
      %>
      <a href="${pageContext.request.contextPath}/posts?page=<%= i %>" class="<%= (i == currentPage) ? "active" : "" %>">
        <%= i %>
      </a>
      <%
        }
      %>
    </div>
  </div>
</div>