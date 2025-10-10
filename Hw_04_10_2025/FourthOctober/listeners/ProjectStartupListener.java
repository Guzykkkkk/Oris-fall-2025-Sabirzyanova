package org.example.FourthOctober.listeners;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.FourthOctober.config.DataBaseConfig;
import org.example.FourthOctober.repository.Impl.UserRepositoryImpl;
import org.example.FourthOctober.repository.UserRepository;
import org.example.FourthOctober.service.AuthDataValidationService;
import org.example.FourthOctober.service.AuthService;
import org.example.FourthOctober.service.Impl.AuthServiceImpl;
import org.example.FourthOctober.service.Impl.RegexAuthDataValidationServiceImpl;
import org.example.FourthOctober.service.Impl.SimpleHashPasswordEncoder;
import org.example.FourthOctober.service.PasswordEncoder;

@WebListener
public class ProjectStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        UserRepository userRepository = new UserRepositoryImpl(DataBaseConfig.jdbcTemplate);
        AuthDataValidationService validationService = new RegexAuthDataValidationServiceImpl();
        PasswordEncoder passwordEncoder = new SimpleHashPasswordEncoder();

        AuthService authService = new AuthServiceImpl(userRepository, validationService, passwordEncoder);
        context.setAttribute("authService", authService);
    }
}
