package org.example.FourthOctober.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.FourthOctober.DAO.ChatRepository;
import org.example.FourthOctober.DAO.Impl.ChatRepositoryImpl;
import org.example.FourthOctober.DAO.Impl.UserRepositoryImpl;
import org.example.FourthOctober.DAO.UserRepository;
import org.example.FourthOctober.config.DataBaseConfig;
import org.example.FourthOctober.service.AuthService;
import org.example.FourthOctober.service.AuthServiceImpl.AuthServiceImpl;
import org.example.FourthOctober.service.AuthServiceImpl.AuthValidationServiceImpl;
import org.example.FourthOctober.service.AuthServiceImpl.ChattingServiceImpl;
import org.example.FourthOctober.service.AuthServiceImpl.PasswordEncoderImpl;
import org.example.FourthOctober.service.AuthValidationService;
import org.example.FourthOctober.service.ChattingService;
import org.example.FourthOctober.service.PasswordEncoder;

@WebListener
public class ProjectStartUpListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        UserRepository userRepository = new UserRepositoryImpl(DataBaseConfig.jdbcTemplate);
        AuthValidationService validationService = new AuthValidationServiceImpl();
        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        ChatRepository chatRepository = new ChatRepositoryImpl();
        ChattingService chattingService = new ChattingServiceImpl(chatRepository);
        context.setAttribute("userRepository", userRepository);

        AuthService authService = new AuthServiceImpl(userRepository, validationService, passwordEncoder);

        context.setAttribute("chattingService", chattingService);
        context.setAttribute("authService", authService);

        System.out.println("Services initialized and added to context:");
        System.out.println(" chattingService: " + chattingService);
        System.out.println(" authService: " + authService);
    }
}