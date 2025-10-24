package org.example.FourthOctober.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.FourthOctober.model.UserEntity;
import org.example.FourthOctober.repository.UserRepository;
import org.example.FourthOctober.service.AvatharService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/users/with-avatars")
public class UserWithAvatarServlet extends HttpServlet {
    private AvatharService avatharService;
    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        avatharService = (AvatharService) getServletContext().getAttribute("avatharService");
        userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<UserEntity> users = userRepository.findAllWithAvatars();

            String jsonResponse = buildUsersJson(users);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            PrintWriter out = resp.getWriter();
            out.print(jsonResponse);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String errorJson = String.format(
                    "{\"success\":false,\"message\":\"%s\"}",
                    escapeJson("Failed to get users list: " + e.getMessage())
            );
            resp.getWriter().print(errorJson);
        }
    }

    private String buildUsersJson(List<UserEntity> users) {
        if (users.isEmpty()) {
            return "[]";
        }

        StringBuilder jsonBuilder = new StringBuilder("[");

        for (int i = 0; i < users.size(); i++) {
            UserEntity user = users.get(i);

            String userJson = String.format(
                    "{\"id\":%d,\"username\":\"%s\",\"email\":\"%s\",\"hasAvatar\":%b,\"avatarUrl\":\"%s\"}",
                    user.getId(),
                    escapeJson(user.getUsername()),
                    escapeJson(user.getEmail()),
                    user.getAvatarFile() != null,
                    user.getAvatarFile() != null ?
                            String.format("/user/avatar?userId=%d", user.getId()) : ""
            );

            jsonBuilder.append(userJson);

            if (i < users.size() - 1) {
                jsonBuilder.append(",");
            }
        }

        jsonBuilder.append("]");
        return jsonBuilder.toString();
    }

    private String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace("/", "\\/");
    }
}