package org.example.FourthOctober.DAO;

import org.example.FourthOctober.model.Message;
import org.example.FourthOctober.model.User;

import java.util.List;

public interface ChatRepository {
    void saveMessages(Message message);
    List<Message> getLastMessages(int count);
}
