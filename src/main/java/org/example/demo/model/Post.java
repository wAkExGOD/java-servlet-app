package org.example.demo.model;

import java.sql.Timestamp;

public class Post {
    private int id;
    private String title;
    private String content;
    private int authorId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Post(int id, String title, String content, int authorId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Конструктор для создания новых постов до занесения в БД
    public Post(String title, String content, int authorId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getAuthorId() {
        return authorId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}