package org.example.FourthOctober.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.FourthOctober.DAO.Impl.UserRepositoryImpl;
import org.example.FourthOctober.DAO.UserRepository;
import org.example.FourthOctober.DTO.FieldErrorDto;
import org.example.FourthOctober.DTO.Request.SignUpRequest;
import org.example.FourthOctober.DTO.Response.AuthResponse;
import org.example.FourthOctober.config.DataBaseConfig;
import org.example.FourthOctober.service.AuthService;
import org.example.FourthOctober.service.AuthServiceImpl.AuthServiceImpl;
import org.example.FourthOctober.service.AuthServiceImpl.AuthValidationServiceImpl;
import org.example.FourthOctober.service.AuthServiceImpl.PasswordEncoderImpl;
import org.example.FourthOctober.service.AuthValidationService;
import org.example.FourthOctober.service.PasswordEncoder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {

    private AuthService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
       
        UserRepository userRepository = new UserRepositoryImpl(DataBaseConfig.jdbcTemplate);
        AuthValidationService authValidationService = new AuthValidationServiceImpl();
        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        authService = new AuthServiceImpl(userRepository, authValidationService, passwordEncoder);

       
        config.getServletContext().setAttribute("authService", authService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FieldErrorDto> errors = (List<FieldErrorDto>) req.getSession().getAttribute("errors");
        if(Objects.nonNull(errors)) {
            req.setAttribute("errors", errors);
       
            req.getSession().removeAttribute("errors");
        }
        req.getRequestDispatcher("/jsp/sign-up.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String nickname = req.getParameter("nickname");
        String date = req.getParameter("date");       

       
        SignUpRequest request = SignUpRequest.builder()
                .email(email)
                .password(password)
                .name(nickname)
                .date(date)       
                .build();

        AuthResponse authResponse = authService.signUp(request);

        if(!authResponse.isSuccess()) {
       
            req.getSession().setAttribute("errors", authResponse.getErrors());
            resp.sendRedirect(req.getContextPath() + "/sign-up");
        } else {
       
            req.getSession().removeAttribute("errors");
            resp.sendRedirect(req.getContextPath() + "/sign-in");
        }
    }
}