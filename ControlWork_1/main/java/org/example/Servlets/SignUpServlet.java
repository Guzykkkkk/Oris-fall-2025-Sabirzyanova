package org.example.Servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DTO.DtoField;
import org.itis.Astrology.Dto.FieldErrorDto;
import org.itis.Astrology.Service.AuthService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/sign-up")
    public class SignUpServlet extends HttpServlet {

        private AuthService authService;

        @Override
        public void init(ServletConfig config) throws ServletException {
            authService = (AuthService) config.getServletContext().getAttribute("authService");
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            if(Objects.nonNull(req.getSession().getAttribute("email"))) {
                resp.sendRedirect("/profile");
            } else {
                List<DtoField> errors = (List<DtoField>) req.getSession().getAttribute("errors");
                if(Objects.nonNull(errors)) {
                    req.setAttribute("errors", errors);
                }
                req.getRequestDispatcher("/jsp/sign-up.jsp").forward(req, resp);
            }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            SignUpRequest request = SignUpRequest.builder()
                    .email(req.getParameter("email"))
                    .password(req.getParameter("password"))
                    .nickname(req.getParameter("nickname"))
                    .build();
            AuthResponse authResponse = authService.signUp(request);
            if (!authResponse.isSuccess()) {
                req.getSession(true).setAttribute("errors", authResponse.getErrors());
                resp.sendRedirect("/sign-up");
            } else {
                req.getSession(true).setAttribute("errors", null);
                resp.sendRedirect("/sign-in");
            }
        }
}
