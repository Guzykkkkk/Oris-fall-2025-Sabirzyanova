package ru.itis.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.entity.CarModel;

import java.util.List;
import java.util.Optional;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {

    @Override
    @EntityGraph(attributePaths = {"trims", "trims.options"})
    List<CarModel> findAll();

    @EntityGraph(attributePaths = {"trims", "trims.options"})
    @Query("select m from CarModel m where m.id = :id")
    Optional<CarModel> findDetailedById(@Param("id") Long id);
}
