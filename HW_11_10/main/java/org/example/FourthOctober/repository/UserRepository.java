package org.example.FourthOctober.repository;

import org.example.FourthOctober.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {


    Optional<UserEntity> findByEmail(String email);

    void save(UserEntity entity);
    void avatarUpdate(Long id, Long entity);
    List<UserEntity> findAllWithAvatars();
    Optional<UserEntity> findById(Long id);

}
