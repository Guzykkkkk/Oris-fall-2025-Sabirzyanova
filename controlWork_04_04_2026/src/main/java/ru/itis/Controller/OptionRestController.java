package ru.itis.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itis.Service.OptionAdminService;
import ru.itis.dto.OptionRequest;
import ru.itis.entity.OptionItem;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/models/{modelId}/trims/{trimId}/options")
public class OptionRestController {

    private final OptionAdminService optionAdminService;

    public OptionRestController(OptionAdminService optionAdminService) {
        this.optionAdminService = optionAdminService;
    }

    @GetMapping
    public List<OptionItem> findAll(@PathVariable Long modelId, @PathVariable Long trimId) {
        return optionAdminService.findAll(modelId, trimId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OptionItem create(@PathVariable Long modelId,
                             @PathVariable Long trimId,
                             @RequestBody OptionRequest request) {
        return optionAdminService.create(modelId, trimId, request);
    }

    @PutMapping("/{optionId}")
    public OptionItem update(@PathVariable Long modelId,
                             @PathVariable Long trimId,
                             @PathVariable Long optionId,
                             @RequestBody OptionRequest request) {
        return optionAdminService.update(modelId, trimId, optionId, request);
    }

    @DeleteMapping("/{optionId}")
    public Map<String, String> delete(@PathVariable Long modelId,
                                      @PathVariable Long trimId,
                                      @PathVariable Long optionId) {
        optionAdminService.delete(modelId, trimId, optionId);
        return Map.of("status", "deleted");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(IllegalArgumentException ex) {
        return Map.of("error", ex.getMessage());
    }
}
