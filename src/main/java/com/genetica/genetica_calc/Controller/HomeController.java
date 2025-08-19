package com.genetica.genetica_calc.Controller;

import com.genetica.genetica_calc.Model.DTO.ContactMessageDTO;
import com.genetica.genetica_calc.Repository.QuizQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final QuizQuestionRepository quizQuestionRepository;

    @GetMapping()
    public String calculator() {
        return "calculator";
    }

    @GetMapping("/theory")
    public String theory() {
        return "theory";
    }

    @GetMapping("/segregation-law")
    public String segregationLaw() {
        return "segregation";
    }

    @GetMapping("/independent-assortment")
    public String independentAssortment() {
        return "independentAssortment";
    }

    @GetMapping("/bac-exercises")
    public String bacExercises() {
        return "bacExercises";
    }

    @GetMapping("/learning-environment")
    public String learningEnvironment(Model model) {
        List<String> categories = quizQuestionRepository.findDistinctCategories();
        model.addAttribute("categories", categories);
        return "learningEnvironment";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contactMessageDTO", new ContactMessageDTO());
        return "contact";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }

    @GetMapping("/terms")
    public String termsAndConditions() {
        return "termsAndConditions";
    }

    @GetMapping("/donate")
    public String donate() {
        return "donate";
    }

    @GetMapping("/donation/success")
    public String donationSuccess() {
        return "donationSuccess";
    }

    @GetMapping("/donation/cancel")
    public String donationCancel() {
        return "donationCancel";
    }

}
