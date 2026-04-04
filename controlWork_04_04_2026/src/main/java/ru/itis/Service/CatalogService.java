package ru.itis.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.Repository.CarModelRepository;
import ru.itis.entity.CarModel;
import ru.itis.entity.TrimLevel;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CatalogService {

    private final CarModelRepository carModelRepository;

    public CatalogService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    @Transactional(readOnly = true)
    public List<CarModel> findAllModels() {
        return carModelRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CarModel findModel(Long id) {
        return carModelRepository.findDetailedById(id)
                .orElseThrow(() -> new IllegalArgumentException("Модель не найдена"));
    }

    @Transactional(readOnly = true)
    public TrimLevel findTrim(Long modelId, Long trimId) {
        CarModel model = findModel(modelId);
        return model.getTrims().stream()
                .filter(trim -> trim.getId().equals(trimId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Комплектация не найдена"));
    }

    public BigDecimal calculatePrice(CarModel model, TrimLevel trim) {
        BigDecimal optionsPrice = trim.getOptions().stream()
                .map(option -> option.getPrice() == null ? BigDecimal.ZERO : option.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return model.getBasePrice()
                .add(trim.getExtraPrice())
                .add(optionsPrice);
    }
}
