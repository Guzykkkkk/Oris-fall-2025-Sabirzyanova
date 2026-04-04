package ru.itis.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.entity.LeadRequest;

public interface LeadRequestRepository extends JpaRepository<LeadRequest, Long> {
}
