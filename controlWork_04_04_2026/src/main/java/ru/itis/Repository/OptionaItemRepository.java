package ru.itis.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.entity.OptionItem;

import java.util.List;
import java.util.Optional;

public interface OptionaItemRepository extends JpaRepository<OptionItem, Long> {
    List<OptionItem> findByTrimLevelId(Long trimLevelId);
    Optional<OptionItem> findByIdAndTrimLevelId(Long id, Long trimLevelId);
}
