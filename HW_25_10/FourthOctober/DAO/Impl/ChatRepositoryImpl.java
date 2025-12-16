package org.example.FourthOctober.DAO.Impl;

import org.example.FourthOctober.DAO.ChatRepository;
import org.example.FourthOctober.model.Message;
import org.example.FourthOctober.model.User;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import static org.example.FourthOctober.config.DataBaseConfig.jdbcTemplate;

public class ChatRepositoryImpl implements ChatRepository {

    private static final String SQL_SAVE_MESSAGE = "INSERT INTO messages (message, time, author_id) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_LAST_MESSAGES =
            "SELECT m.id, m.message, m.time, m.author_id, u.name as author_name " +
                    "FROM messages m " +
                    "JOIN chatting_users u ON m.author_id = u.id " +
                    "ORDER BY m.time ASC LIMIT ?";

    @Override
    public void saveMessages(Message message) {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement preparedStatement = con.prepareStatement(SQL_SAVE_MESSAGE, new String[]{"id"});
                preparedStatement.setString(1, message.getMessage());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(message.getTime()));
                preparedStatement.setLong(3, message.getAuthor().getId());
                return preparedStatement;
            }, keyHolder);

  
            if (keyHolder.getKey() != null) {
                message.setId(keyHolder.getKey().longValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving message to database", e);
        }
    }

    @Override
    public List<Message> getLastMessages(int count) {
        try {
            return jdbcTemplate.query(SQL_SELECT_LAST_MESSAGES, (rs, rowNum) -> {
               
                User author = User.builder()
                        .id(rs.getLong("author_id"))
                        .name(rs.getString("author_name"))
                        .build();

  
                return Message.builder()
                        .id(rs.getLong("id"))
                        .message(rs.getString("message"))
                        .time(rs.getTimestamp("time").toLocalDateTime())
                        .author(author)
                        .build();
            }, count);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving messages from database", e);
        }
    }
}