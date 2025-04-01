<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="upload-container">
    <div class="upload-section">
        <h2>Загрузка аватара</h2>
        <form action="${pageContext.request.contextPath}/upload" method="post" enctype="multipart/form-data" class="upload-form">
            <div class="form-group">
                <label for="avatar" class="file-label">
                    <span class="file-label-text">Выберите изображение</span>
                    <input type="file" id="avatar" name="avatar" accept="image/*" class="file-input" required>
                </label>
                <div id="preview" class="image-preview"></div>
            </div>
            <button type="submit" class="btn btn-primary">Загрузить</button>
        </form>
    </div>

    <div class="current-avatar-section">
        <h3>Текущий аватар</h3>
        <div class="current-avatar">
            <%
                org.example.demo.dao.UserAvatarDao userAvatarDao = new org.example.demo.dao.UserAvatarDao();
                org.example.demo.model.UserAvatar avatar = userAvatarDao.findByUserId((Long) session.getAttribute("userId"));
                if (avatar != null) {
            %>
                <img src="${pageContext.request.contextPath}/uploads/<%= avatar.getFileName() %>" 
                     alt="User Avatar" 
                     class="avatar-image">
            <% } else { %>
                <p class="no-avatar">Аватар не загружен</p>
            <% } %>
        </div>
    </div>
</div>

<script>
document.getElementById('avatar').addEventListener('change', function(e) {
    const preview = document.getElementById('preview');
    preview.innerHTML = '';
    
    if (this.files && this.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const img = document.createElement('img');
            img.src = e.target.result;
            img.className = 'preview-image';
            preview.appendChild(img);
        }
        reader.readAsDataURL(this.files[0]);
    }
});
</script> 