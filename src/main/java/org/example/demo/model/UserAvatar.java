package org.example.demo.model;

import java.sql.Timestamp;

public class UserAvatar {
    private Long id;
    private Long userId;
    private String fileName;
    private String filePath;
    private String fileType;
    private Timestamp uploadDate;

    // Default constructor
    public UserAvatar() {
    }

    // Constructor with all fields except id and uploadDate
    public UserAvatar(Long userId, String fileName, String filePath, String fileType) {
        this.userId = userId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    // Full constructor
    public UserAvatar(Long id, Long userId, String fileName, String filePath, String fileType, Timestamp uploadDate) {
        this.id = id;
        this.userId = userId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.uploadDate = uploadDate;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }
}