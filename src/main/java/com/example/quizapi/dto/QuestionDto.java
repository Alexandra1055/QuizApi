package com.example.quizapi.dto;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private String question;
    private ArrayList<String> answers;

    public void setAnswers(ArrayList<String> incorrectAnswers, String correctAnswer) {
        answers = new ArrayList<>();

        answers.add(correctAnswer);
        answers.addAll(incorrectAnswers);

        Collections.shuffle(answers);
    }
}