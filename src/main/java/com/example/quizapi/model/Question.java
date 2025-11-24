package com.example.quizapi.model;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String correctAnswer;
    private ArrayList<String> incorrectAnswers;
    private String question;
}


