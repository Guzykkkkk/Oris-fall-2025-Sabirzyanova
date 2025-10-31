package org.example.FourthOctober.servlets;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.SneakyThrows;
import org.example.FourthOctober.config.ServiceLocator;
import org.example.FourthOctober.config.WebSocketConfigurator;
import org.example.FourthOctober.model.User;
import org.example.FourthOctober.service.ChattingService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/ws", configurator = WebSocketConfigurator.class)
public class WebSocketChatEndpoint {

    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();
    private static final Map<String, User> sessionUsers = new ConcurrentHashMap<>();

        
    private final ChattingService chatService = ServiceLocator.getChattingService();

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("WebSocket OPEN - ID: " + session.getId());

        HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
        System.out.println(" HTTP Session: " + httpSession);

        if (httpSession != null) {
            User user = (User) httpSession.getAttribute("user");
            System.out.println(" User from session: " + user);

            if (user != null) {
                sessionUsers.put(session.getId(), user);
                System.out.println(" User authenticated: " + user.getName());

        
                String welcomeMsg = "[" + LocalDateTime.now().format(TIME_FORMATTER) + "] " +
                        "[System]: Welcome " + user.getName() + "!";
                try {
                    session.getBasicRemote().sendText(welcomeMsg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("HTTP Session is NULL");
        }

        sessions.add(session);
        System.out.println(" Total sessions: " + sessions.size());
    }

    @OnMessage
    @SneakyThrows
    public void onMessage(String message, Session sender) {
        System.out.println(" WebSocket MESSAGE - ID: " + sender.getId());
        System.out.println(" Message: '" + message + "'");

        User user = sessionUsers.get(sender.getId());

        
        if (user == null) {
            user = User.builder()
                    .name("Guest_" + sender.getId().substring(0, 8))
                    .build();
            sessionUsers.put(sender.getId(), user);
            System.out.println(" Created temporary user: " + user.getName());
        }

        if (message != null && !message.trim().isEmpty()) {
            String trimmedMessage = message.trim();

            try {
        
                chatService.sendMessage(trimmedMessage, user);
                System.out.println("Message saved to database");
            } catch (Exception e) {
                System.err.println(" Error saving message: " + e.getMessage());
                e.printStackTrace();
            }

        
            String formattedMessage = "[" + LocalDateTime.now().format(TIME_FORMATTER) + "] " +
                    user.getName() + ": " + trimmedMessage;

            System.out.println("Broadcasting: " + formattedMessage);
            broadcastMessage(formattedMessage, null);

        } else {
            System.out.println(" Empty message received");
        }
    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("WebSocket error: ID = " + session.getId() +
                ", error = " + throwable.getMessage());
        throwable.printStackTrace();

        sessions.remove(session);
        sessionUsers.remove(session.getId());
    }

    private void broadcastMessage(String message, String excludeSessionId) {
        for (Session target : sessions) {
            if (target.isOpen() && !target.getId().equals(excludeSessionId)) {
                try {
                    target.getBasicRemote().sendText(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int getOnlineUsersCount() {
        return sessionUsers.size();
    }

    public static Set<String> getOnlineUsers() {
        return Set.copyOf(sessionUsers.values().stream()
                .map(User::getName)
                .toList());
    }
}