package ru.itis.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.Repository.OptionaItemRepository;
import ru.itis.Repository.TrimLevelRepository;
import ru.itis.dto.OptionRequest;
import ru.itis.entity.OptionItem;
import ru.itis.entity.TrimLevel;

import java.util.List;

@Service
public class OptionAdminService {

    private final TrimLevelRepository trimLevelRepository;
    private final OptionaItemRepository optionItemRepository;

    public OptionAdminService(TrimLevelRepository trimLevelRepository, OptionaItemRepository optionItemRepository) {
        this.trimLevelRepository = trimLevelRepository;
        this.optionItemRepository = optionItemRepository;
    }

    @Transactional(readOnly = true)
    public List<OptionItem> findAll(Long modelId, Long trimId) {
        checkTrim(modelId, trimId);
        return optionItemRepository.findByTrimLevelId(trimId);
    }

    @Transactional
    public OptionItem create(Long modelId, Long trimId, OptionRequest request) {
        TrimLevel trim = checkTrim(modelId, trimId);
        OptionItem option = new OptionItem();
        option.setName(request.getName());
        option.setPrice(request.getPrice());
        option.setTrimLevel(trim);
        return optionItemRepository.save(option);
    }

    @Transactional
    public OptionItem update(Long modelId, Long trimId, Long optionId, OptionRequest request) {
        checkTrim(modelId, trimId);
        OptionItem option = optionItemRepository.findByIdAndTrimLevelId(optionId, trimId)
                .orElseThrow(() -> new IllegalArgumentException("Опция не найдена"));
        option.setName(request.getName());
        option.setPrice(request.getPrice());
        return optionItemRepository.save(option);
    }

    @Transactional
    public void delete(Long modelId, Long trimId, Long optionId) {
        checkTrim(modelId, trimId);
        OptionItem option = optionItemRepository.findByIdAndTrimLevelId(optionId, trimId)
                .orElseThrow(() -> new IllegalArgumentException("Опция не найдена"));
        optionItemRepository.delete(option);
    }

    private TrimLevel checkTrim(Long modelId, Long trimId) {
        return trimLevelRepository.findByIdAndCarModelId(trimId, modelId)
                .orElseThrow(() -> new IllegalArgumentException("Комплектация не найдена"));
    }
}
