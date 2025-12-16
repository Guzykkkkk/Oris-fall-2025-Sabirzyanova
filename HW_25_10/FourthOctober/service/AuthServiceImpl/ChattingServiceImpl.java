package org.example.FourthOctober.service.AuthServiceImpl;


import lombok.RequiredArgsConstructor;
import org.example.FourthOctober.DAO.ChatRepository;
import org.example.FourthOctober.DAO.Impl.ChatRepositoryImpl;
import org.example.FourthOctober.model.Message;
import org.example.FourthOctober.model.User;
import org.example.FourthOctober.service.ChattingService;


import java.time.LocalDateTime;
import java.util.List;



@RequiredArgsConstructor
public class ChattingServiceImpl implements ChattingService {
    public ChattingServiceImpl() {
        this.chatRepository = new ChatRepositoryImpl();
    }

    private final ChatRepository chatRepository;

    @Override
    public List<Message> getChatHistory() {
        try {
            return chatRepository.getLastMessages(100);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving chat history", e);
        }
    }

    @Override
    public void sendMessage(String text, User author) {
        try {
            Message message = Message.builder()
                    .message(text)
                    .author(author)
                    .time(LocalDateTime.now())
                    .build();

            chatRepository.saveMessages(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving message", e);
        }
    }
}