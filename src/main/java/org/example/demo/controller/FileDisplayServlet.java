package org.example.demo.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.demo.model.UserAvatar;
import org.example.demo.service.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/uploads/*")
public class FileDisplayServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(FileDisplayServlet.class);
    private FileService fileService;

    @Override
    public void init() throws ServletException {
        fileService = new FileService();
        fileService.setServletContext(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestPath = request.getPathInfo();
        if (requestPath == null || requestPath.equals("/")) {
            logger.error("No file specified in request");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No file specified");
            return;
        }

        String fileName = requestPath.substring(1);
        logger.info("Requested file: {}", fileName);

        String uploadPath = getServletContext().getRealPath("") + "uploads";
        logger.info("Looking for file in: {}", uploadPath);

        File file = new File(uploadPath, fileName);
        logger.info("Full file path: {}", file.getAbsolutePath());

        if (!file.exists()) {
            logger.error("File not found: {}", file.getAbsolutePath());
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
                OutputStream outputStream = response.getOutputStream()) {

            String contentType = getServletContext().getMimeType(fileName);
            if (contentType == null) {
                contentType = "image/*";
            }

            response.setContentType(contentType);
            response.setHeader("Cache-Control", "public, max-age=31536000");

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            logger.info("File served successfully: {}", fileName);
        } catch (IOException e) {
            logger.error("Error serving file: {}", fileName, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error serving file");
        }
    }
}