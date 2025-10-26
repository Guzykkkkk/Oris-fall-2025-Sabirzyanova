package org.example.FourthOctober.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.FourthOctober.model.UserEntity;
import org.example.FourthOctober.repository.UserRepository;
import org.example.FourthOctober.service.FileInfoService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/user/avatar")
public class UserAvatarServlet extends HttpServlet {
    private UserRepository userRepository;
    private FileInfoService fileInfoService;

    @Override
    public void init() throws ServletException {
        userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
        fileInfoService = (FileInfoService) getServletContext().getAttribute("fileInfoService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("userId");
        if (userIdParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID required");
            return;
        }

        try {
            Long userId = Long.parseLong(userIdParam);
            Optional<UserEntity> user = userRepository.findById(userId);

            if (user.isEmpty() || user.get().getAvatarFile() == null) {
                resp.sendRedirect("/images/default-avatar.png");
                return;
            }

            resp.sendRedirect("/avatar/" + user.get().getAvatarFile());

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to get avatar");
        }
    }
}