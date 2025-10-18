package org.example.Service;

import lombok.Builder;
import org.example.DAO.Tasking;
import org.example.Model.Task;

import java.util.List;

public class UserService {
    @Builder
    private final Tasking TASKING;

    public List<Task> getUserTasks(String userEmail) {
        return TASKING.findByUserEmail(userEmail);
    }

    public Task createTask(String title, String description, String userEmail) {
        Task task = new Task(title, description, userEmail);
        return TASKING.save(task);
    }

    public boolean updateTaskStatus(Long taskId, TaskStatus newStatus, String userEmail) {
        Task task = getTaskById(taskId);
        if (task != null && task.getUserEmail().equals(userEmail)) {
            task.setStatus(newStatus);
            return TASKING.update(task);
        }
        return false;
    }

    public boolean deleteTask(Long taskId, String userEmail) {
        Task task = getTaskById(taskId);
        if (task != null && task.getUserEmail().equals(userEmail)) {
            return TASKING.deleteById(taskId);
        }
        return false;
    }

    public Task getTaskById(Long taskId) {
        return TASKING.findById(taskId).orElse(null);
    }
    public boolean updateTask(Task task, String userEmail) {
        if (task != null && task.getUserEmail().equals(userEmail)) {
            return TASKING.update(task);
        }
        return false;
    }

}
