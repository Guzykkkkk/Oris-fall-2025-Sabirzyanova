package org.example.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession(true).setAttribute("errors", null);
        if(Objects.isNull(req.getSession().getAttribute("email"))) {
            resp.sendRedirect("/sign-in");
        } else {
            req.setAttribute("email", req.getSession().getAttribute("email"));
            req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
        }
    }
}
