package com.genetica.genetica_calc.Service;

import com.genetica.genetica_calc.Model.QuizQuestion;
import com.genetica.genetica_calc.Repository.QuizQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizQuestionRepository quizQuestionRepository;

    public List<QuizQuestion> getQuestionsByCategory(String category) {
        return quizQuestionRepository.findByCategory(category);
    }
}
