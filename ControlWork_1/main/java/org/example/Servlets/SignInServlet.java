package org.example.Servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DTO.DtoField;
import org.example.DTO.requests.SignInReq;
import org.example.DTO.respons.AuthRes;
import org.example.Service.AuthService;


import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/sign-in")
public class SignInServlet extends HttpServlet {

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
            req.getRequestDispatcher("/jsp/sign-in.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignInReq request = SignInReq.builder()
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .build();
        AuthRes authResponse = authService.signIn(request);
        if(!authResponse.isSuccess()) {
            req.getSession(true).setAttribute("errors", authResponse.getErrors());
            resp.sendRedirect("/sign-in");
        } else {
            req.getSession(true).setAttribute("errors", null);
            req.getSession().setAttribute("email", request.getEmail());
            resp.sendRedirect("/profile");
        }
    }
}
