package org.example.demo.service;

import jakarta.servlet.ServletContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.demo.dao.UserAvatarDao;
import org.example.demo.model.UserAvatar;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileService {
    private static final Logger logger = LogManager.getLogger(FileService.class);
    private static final String UPLOAD_DIRECTORY = "uploads";
    private final UserAvatarDao userAvatarDao;
    private ServletContext servletContext;

    public FileService() {
        this.userAvatarDao = new UserAvatarDao();
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public UserAvatar saveUserAvatar(Part filePart, Long userId) throws IOException {
        String fileName = getFileName(filePart);
        if (fileName == null || fileName.isEmpty()) {
            logger.error("No file was uploaded");
            return null;
        }

        String fileExtension = getFileExtension(fileName);
        if (!isValidImageExtension(fileExtension)) {
            logger.error("Invalid file type: {}", fileExtension);
            return null;
        }

        String uniqueFileName = generateUniqueFileName(fileExtension);
        String uploadPath = getUploadPath();
        createUploadDirectoryIfNotExists(uploadPath);

        String filePath = saveFile(filePart, uploadPath, uniqueFileName);
        if (filePath == null) {
            return null;
        }

        UserAvatar avatar = new UserAvatar();
        avatar.setUserId(userId);
        avatar.setFileName(uniqueFileName);
        avatar.setFilePath(filePath);
        avatar.setFileType(filePart.getContentType());

        userAvatarDao.save(avatar);
        logger.info("Avatar saved successfully for user: {}", userId);
        return avatar;
    }

    public UserAvatar getUserAvatar(Long userId) {
        return userAvatarDao.findByUserId(userId);
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex > 0 ? fileName.substring(lastDotIndex + 1).toLowerCase() : "";
    }

    private boolean isValidImageExtension(String extension) {
        return extension.matches("jpg|jpeg|png|gif");
    }

    private String generateUniqueFileName(String extension) {
        return UUID.randomUUID().toString() + "." + extension;
    }

    private String getUploadPath() {
        if (servletContext == null) {
            throw new IllegalStateException("ServletContext not set");
        }
        String realPath = servletContext.getRealPath("");
        logger.info("Real path: {}", realPath);
        return realPath + UPLOAD_DIRECTORY;
    }

    private void createUploadDirectoryIfNotExists(String uploadPath) throws IOException {
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
            logger.info("Created upload directory: {}", uploadPath);
        }
    }

    private String saveFile(Part filePart, String uploadPath, String fileName) throws IOException {
        String filePath = uploadPath + File.separator + fileName;
        logger.info("Attempting to save file to: {}", filePath);

        try {
            filePart.write(filePath);
            logger.info("File saved successfully: {}", filePath);
            return UPLOAD_DIRECTORY + "/" + fileName;
        } catch (IOException e) {
            logger.error("Failed to save file: {}", e.getMessage(), e);
            throw e;
        }
    }
}