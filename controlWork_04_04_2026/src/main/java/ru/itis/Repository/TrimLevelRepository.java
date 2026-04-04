package ru.itis.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.entity.TrimLevel;

import java.util.Optional;

public interface TrimLevelRepository extends JpaRepository<TrimLevel, Long> {
    Optional<TrimLevel> findByIdAndCarModelId(Long id, Long carModelId);
}
