package org.example.FourthOctober.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.FourthOctober.service.FileInfoService;

import java.io.IOException;

@WebServlet("/avatar/*")
    public class AvatarDownloadServlet extends HttpServlet {
    private FileInfoService fileInfoService;

    @Override
    public void init() throws ServletException {
        fileInfoService = (FileInfoService) getServletContext().getAttribute("fileInfoService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Storage name required");
            return;
        }

        String storageName = pathInfo.substring(1);

        try {
            resp.setContentType("image/jpeg");
            resp.setHeader("Content-Disposition", "inline");

            fileInfoService.writeFile(storageName, resp.getOutputStream());
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Avatar not found");
        }

    }

}
