package org.example.FourthOctober.service;

import org.example.FourthOctober.model.Message;
import org.example.FourthOctober.model.User;

import java.util.List;

public interface ChattingService {
    List<Message> getChatHistory();
    void sendMessage(String text, User author);
}
