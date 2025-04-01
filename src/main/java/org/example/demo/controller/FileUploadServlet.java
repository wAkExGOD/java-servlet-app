package org.example.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.demo.service.FileService;

import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 15 // 15 MB
)
public class FileUploadServlet extends BaseServlet {
    private static final Logger logger = LogManager.getLogger(FileUploadServlet.class);
    private FileService fileService;

    @Override
    public void init() throws ServletException {
        fileService = new FileService();
        fileService.setServletContext(getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Starting file upload process");

        Integer userIdInt = (Integer) request.getSession().getAttribute("userId");
        if (userIdInt == null) {
            logger.error("User not authenticated");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authenticated");
            return;
        }

        Long userId = userIdInt.longValue();
        logger.info("Processing upload for user ID: {}", userId);

        Part filePart = request.getPart("avatar");
        if (filePart == null || filePart.getSize() == 0) {
            logger.error("No file part received");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No file uploaded");
            return;
        }

        try {
            fileService.saveUserAvatar(filePart, userId);
            response.sendRedirect(request.getContextPath() + "/profile");
        } catch (IOException e) {
            logger.error("Error saving file", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving file");
        }
    }
}