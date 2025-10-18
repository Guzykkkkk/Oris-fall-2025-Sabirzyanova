package org.example.DAO;

import org.example.Model.Task;

import java.util.List;
import java.util.Optional;

public interface Tasking {
    Task Save(Task task);
    Optional<Task> findById(Long id);
    Optional<Task> deleteById(Long id);
    List<Task> findByUserEmail(String email);
    boolean update(Task task);
    boolean delete(Task task);



}
