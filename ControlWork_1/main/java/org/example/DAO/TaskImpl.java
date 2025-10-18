package org.example.DAO;

import org.example.Model.Task;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TaskImpl  implements Tasking {
    private static final String SAVE_SQL = """
        INSERT INTO tasks (title, description, status, user_email, created_at) 
        VALUES (?, ?, ?, ?, ?)
        """;

    private static final String FIND_BY_ID_SQL = "SELECT * FROM tasks WHERE id = ?";

    private static final String FIND_BY_USER_EMAIL_SQL = "SELECT * FROM tasks WHERE user_email = ? ORDER BY created_at DESC";

    private static final String UPDATE_SQL = """
        UPDATE tasks SET title = ?, description = ?, status = ?, updated_at = ? 
        WHERE id = ? AND user_email = ?
        """;

    private static final String DELETE_BY_ID_SQL = "DELETE FROM tasks WHERE id = ? AND user_email = ?";

    private static final String DELETE_ALL_BY_USER_EMAIL_SQL = "DELETE FROM tasks WHERE user_email = ?";
    public Task Save(Task task) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getStatus().getValue());
            preparedStatement.setString(4, task.getUserEmail());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(task.getCreatedAt()));

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setId(generatedKeys.getLong(1));
            }

            return task;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving task", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Task> findById(Long id) {
        try (Connection connection = ConnectionManager.open();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(resSetToTask(resultSet));
            }

            return Optional.empty();
        return Optional.empty();
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }

}
