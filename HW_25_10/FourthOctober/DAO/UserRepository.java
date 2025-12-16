package org.example.FourthOctober.DAO;

import org.example.FourthOctober.model.User;

import java.util.Optional;


public interface UserRepository {
    void save(User entity);
    Optional<User> findByEmail(String email);
}
