package ru.itis.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.Repository.LeadRequestRepository;
import ru.itis.dto.LeadForm;
import ru.itis.entity.LeadRequest;

import java.time.LocalDateTime;

@Service
public class LeadService {

    private final LeadRequestRepository leadRequestRepository;

    public LeadService(LeadRequestRepository leadRequestRepository) {
        this.leadRequestRepository = leadRequestRepository;
    }

    @Transactional
    public void save(LeadForm form) {
        LeadRequest lead = new LeadRequest();
        lead.setPhoneNumber(form.getPhoneNumber());
        lead.setPageSource(form.getPageSource());
        lead.setCreatedAt(LocalDateTime.now());
        leadRequestRepository.save(lead);
    }
}
