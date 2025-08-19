package com.genetica.genetica_calc.Controller;

import com.genetica.genetica_calc.Model.QuizQuestion;
import com.genetica.genetica_calc.Service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{category}")
    public String showQuiz(@PathVariable String category, Model model) {
        List<QuizQuestion> questions = quizService.getQuestionsByCategory(category);
        model.addAttribute("questions", questions);
        model.addAttribute("quizName", category);
        return "quiz";
    }

    @PostMapping("/submit")
    public String submitQuiz(
            @RequestParam String quizName,
            @RequestParam int totalQuestions,
            @RequestParam int correctAnswers,
            @RequestParam int wrongAnswers,
            Model model) {

        model.addAttribute("quizName", quizName);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("wrongAnswers", wrongAnswers);

        return "quizResult";
    }

}
