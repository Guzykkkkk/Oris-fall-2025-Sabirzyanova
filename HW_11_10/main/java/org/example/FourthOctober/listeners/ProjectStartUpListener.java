package org.example.FourthOctober.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.FourthOctober.config.ManagerConnection;
import org.example.FourthOctober.repository.FileInfoRepository;
import org.example.FourthOctober.repository.Impl.FileInfoRepositoryImpl;
import org.example.FourthOctober.repository.Impl.UserRepositoryImpl;
import org.example.FourthOctober.repository.UserRepository;
import org.example.FourthOctober.service.*;
import org.example.FourthOctober.service.Impl.*;

@WebListener
public class ProjectStartUpListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        UserRepository userRepository = new UserRepositoryImpl(ManagerConnection.jdbcTemplate);
        FileInfoRepository fileInfoRepository = new FileInfoRepositoryImpl();
        FileInfoService fileInfoService = new FileInfoServiceImpl(fileInfoRepository);

        AuthDataValidationService validationService = new RegexAuthDataValidationServiceImpl();
        PasswordEncoder passwordEncoder = new SimpleHashPasswordEncoder();

        AuthService authService = new AuthServiceImpl(userRepository, validationService, passwordEncoder);

        AvatharService avatharService = new AvatharServiceImple(fileInfoService, fileInfoRepository, userRepository);

        context.setAttribute("authService", authService);
        context.setAttribute("avatharService", avatharService);
        context.setAttribute("fileInfoService", fileInfoService);
        context.setAttribute("userRepository", userRepository);
        context.setAttribute("fileInfoRepository", fileInfoRepository);
    }
}