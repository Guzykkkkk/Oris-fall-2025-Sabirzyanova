package org.example.FourthOctober.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.FourthOctober.dto.FileInfoDto;
import org.example.FourthOctober.service.AvatharService;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/avatar/upload")
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class AvatarUploadServlet extends HttpServlet {
    private AvatharService avatharService;

    @Override
    public void init() throws ServletException {
        avatharService = (AvatharService) getServletContext().getAttribute("avatharService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        if (userId == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authenticated");
            return;
        }

        Part filePart = req.getPart("avatar");
        if (filePart == null || filePart.getSize() == 0) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No file uploaded");
            return;
        }

        try (InputStream fileContent = filePart.getInputStream()) {
            FileInfoDto fileInfoDto = FileInfoDto.builder()
                    .fileInputStream(fileContent)
                    .initialName(filePart.getSubmittedFileName())
                    .mime(filePart.getContentType())
                    .length(filePart.getSize())
                    .build();

            avatharService.updateUserAvathar(userId, fileInfoDto);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Avatar updated successfully");

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to upload avatar");
        }
    }
}
