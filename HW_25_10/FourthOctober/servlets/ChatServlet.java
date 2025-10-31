package org.example.FourthOctober.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.FourthOctober.config.ServiceLocator;
import org.example.FourthOctober.model.Message;
import org.example.FourthOctober.model.User;
import org.example.FourthOctober.service.ChattingService;

import java.io.IOException;
import java.util.List;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
    private ChattingService chattingService;

    @Override
    public void init() throws ServletException {
     
        this.chattingService = ServiceLocator.getChattingService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(false);

     
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/sign-in");
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
     
            List<Message> messages = chattingService.getChatHistory();
            req.setAttribute("messages", messages);
            req.setAttribute("user", user);

            req.getRequestDispatcher("/jsp/chat.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error loading chat history");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

     
        if (session == null || session.getAttribute("user") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        User user = (User) session.getAttribute("user");
        String messageText = req.getParameter("message");

        if (messageText != null && !messageText.trim().isEmpty()) {
            try {
                chattingService.sendMessage(messageText.trim(), user);
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}