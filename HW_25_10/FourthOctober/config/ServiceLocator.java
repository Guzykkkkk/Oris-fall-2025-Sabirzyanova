
package org.example.FourthOctober.config;

import org.example.FourthOctober.DAO.ChatRepository;
import org.example.FourthOctober.DAO.Impl.ChatRepositoryImpl;
import org.example.FourthOctober.service.ChattingService;
import org.example.FourthOctober.service.AuthServiceImpl.ChattingServiceImpl;

public class ServiceLocator {
    private static ChatRepository chatRepository;
    private static ChattingService chattingService;

    static {
        System.out.println("Initializing ServiceLocator...");
        try {

            chatRepository = new ChatRepositoryImpl();
            chattingService = new ChattingServiceImpl(chatRepository);
            System.out.println(" ServiceLocator initialized successfully");
        } catch (Exception e) {
            System.err.println(" Error initializing ServiceLocator: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ChattingService getChattingService() {
        System.out.println(" Getting chattingService from ServiceLocator: " + chattingService);
        return chattingService;
    }

    public static ChatRepository getChatRepository() {
        return chatRepository;
    }
}