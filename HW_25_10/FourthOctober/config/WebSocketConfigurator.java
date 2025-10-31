package org.example.FourthOctober.config;


import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

public class WebSocketConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig config,
                                HandshakeRequest request,
                                HandshakeResponse response) {
        System.out.println("WebSocket Configurator called");
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        System.out.println("HTTP Session: " + httpSession);
        config.getUserProperties().put("httpSession", httpSession);
    }
}