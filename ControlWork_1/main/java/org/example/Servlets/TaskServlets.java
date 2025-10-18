package org.example.Servlets;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlets extends HttpServlet {

    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userEmail = (String) req.getSession().getAttribute("email");
        List<Task> tasks = userService.getUserTasks(userEmail);

        req.setAttribute("tasks", tasks);
        req.getRequestDispatcher("/jsp/tasks.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String userEmail = (String) req.getSession().getAttribute("email");

        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/tasks");
            return;
        }

        switch (action) {
            case "create":
                createTask(req, userEmail);
                break;

            case "updateStatus":
                updateTaskStatus(req, userEmail);
                break;

            case "update":
                updateTask(req, userEmail);
                break;

            case "delete":
                deleteTask(req, userEmail);
                break;
        }

        resp.sendRedirect(req.getContextPath() + "/tasks");
    }
    private void createTask(HttpServletRequest req, String userEmail) {
        String title = req.getParameter("title");
        String description = req.getParameter("description");

        if (title != null && !title.trim().isEmpty()) {
            userService.createTask(title.trim(), description, userEmail);
        }
    }

    private void updateTaskStatus(HttpServletRequest req, String userEmail) {
        try {
            Long taskId = Long.parseLong(req.getParameter("taskId"));
            Task task = userService.getTaskById(taskId);
            if (task != null && task.getUserEmail().equals(userEmail)) {
                TaskStatus newStatus = task.getStatus().getNextStatus();
                userService.updateTaskStatus(taskId, newStatus, userEmail);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid task ID format: " + req.getParameter("taskId"));
        }
    }

    private void updateTask(HttpServletRequest req, String userEmail) {
        try {
            Long taskId = Long.parseLong(req.getParameter("taskId"));
            String title = req.getParameter("title");
            String description = req.getParameter("description");

            Task task = userService.getTaskById(taskId);
            if (task != null && task.getUserEmail().equals(userEmail)) {
                if (title != null && !title.trim().isEmpty()) {
                    task.setTitle(title.trim());
                }
                if (description != null) {
                    task.setDescription(description);
                }
                userService.updateTask(task, userEmail);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid task ID format: " + req.getParameter("taskId"));
        }
    }
    private void deleteTask(HttpServletRequest req, String userEmail) {
        try {
            Long taskId = Long.parseLong(req.getParameter("taskId"));
            userService.deleteTask(taskId, userEmail);
        } catch (NumberFormatException e) {
            System.err.println("Invalid task ID format: " + req.getParameter("taskId"));
        }
    }
}
