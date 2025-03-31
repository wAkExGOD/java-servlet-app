<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="error-container">
    <h2>Ошибка</h2>
    <p class="error-message">${errorMessage}</p>
    <div class="error-actions">
        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Вернуться на главную</a>
    </div>
</div>
