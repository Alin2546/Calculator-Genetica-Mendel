package com.genetica.genetica_calc.Repository;

import com.genetica.genetica_calc.Model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {
    List<QuizQuestion> findByCategory(String category);

    @Query("SELECT DISTINCT q.category FROM QuizQuestion q")
    List<String> findDistinctCategories();

    List<QuizQuestion> findByCategoryOrderByIdAsc(String category);
}
