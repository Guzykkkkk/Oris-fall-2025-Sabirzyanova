package org.example.FourthOctober.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.FourthOctober.DTO.FieldErrorDto;
import org.example.FourthOctober.DTO.Request.SignInRequest;
import org.example.FourthOctober.DTO.Response.AuthResponse;
import org.example.FourthOctober.model.User;
import org.example.FourthOctober.service.AuthService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/sign-in")
public class SignInServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
      
        authService = (AuthService) config.getServletContext().getAttribute("authService");

        if (authService == null) {
            throw new ServletException("AuthService not found in application context");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FieldErrorDto> errors = (List<FieldErrorDto>) req.getSession().getAttribute("errors");
        if(Objects.nonNull(errors)) {
            req.setAttribute("errors", errors);
      
            req.getSession().removeAttribute("errors");
        }
        req.getRequestDispatcher("/jsp/sign-in.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignInRequest request = SignInRequest.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();

        AuthResponse authResponse = authService.signIn(request);

        if(!authResponse.isSuccess()) {
            req.getSession(true).setAttribute("errors", authResponse.getErrors());
            resp.sendRedirect(req.getContextPath() + "/sign-in");
        } else {
            req.getSession(true).setAttribute("errors", null);
            User signedUp = authResponse.getUser();
            req.getSession(true).setAttribute("user", signedUp);
            resp.sendRedirect(req.getContextPath() + "/chat");
        }
    }
}