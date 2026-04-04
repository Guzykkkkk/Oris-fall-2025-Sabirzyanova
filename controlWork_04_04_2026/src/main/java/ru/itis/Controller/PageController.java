package ru.itis.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.Service.CatalogService;
import ru.itis.Service.LeadService;
import ru.itis.dto.LeadForm;
import ru.itis.entity.CarModel;
import ru.itis.entity.TrimLevel;

import javax.validation.Valid;


@Controller
public class PageController {

    private final CatalogService catalogService;
    private final LeadService leadService;

    public PageController(CatalogService catalogService, LeadService leadService) {
        this.catalogService = catalogService;
        this.leadService = leadService;
    }

    @ModelAttribute("leadForm")
    public LeadForm leadForm() {
        return new LeadForm();
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("models", catalogService.findAllModels());
        model.addAttribute("pageTitle", "F-мобиль");
        return "home";
    }

    @GetMapping("/models")
    public String models(Model model) {
        model.addAttribute("models", catalogService.findAllModels());
        model.addAttribute("pageTitle", "Модели");
        return "models";
    }

    @GetMapping("/models/{id}")
    public String model(@PathVariable Long id, Model model) {
        model.addAttribute("car", catalogService.findModel(id));
        model.addAttribute("pageTitle", "Конфигуратор");
        return "configuration";
    }

    @GetMapping("/models/{modelId}/summary/{trimId}")
    public String summary(@PathVariable Long modelId, @PathVariable Long trimId, Model model) {
        CarModel car = catalogService.findModel(modelId);
        TrimLevel trim = catalogService.findTrim(modelId, trimId);
        model.addAttribute("car", car);
        model.addAttribute("trim", trim);
        model.addAttribute("totalPrice", catalogService.calculatePrice(car, trim));
        model.addAttribute("pageTitle", "Итоговая конфигурация");
        return "summary";
    }

    @PostMapping("/lead")
    public String lead(@Valid @ModelAttribute("leadForm") LeadForm form,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("leadError", "Проверьте номер телефона");
            return "redirect:" + (form.getPageSource() == null || form.getPageSource().isBlank() ? "/" : form.getPageSource());
        }

        leadService.save(form);
        redirectAttributes.addFlashAttribute("leadSuccess", "Спасибо! Мы вам перезвоним.");
        return "redirect:" + (form.getPageSource() == null || form.getPageSource().isBlank() ? "/" : form.getPageSource());
    }
}
