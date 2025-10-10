package org.example.FourthOctober.repository;

import org.example.FourthOctober.model.UserEntity;

import java.util.Optional;

public interface UserRepository {

    Optional<UserEntity> findByEmail(String email);

    void save(UserEntity entity);

}
