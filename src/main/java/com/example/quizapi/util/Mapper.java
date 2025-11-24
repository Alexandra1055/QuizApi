package com.example.quizapi.util;

import com.example.quizapi.dto.QuestionDto;
import com.example.quizapi.model.Question;

public class Mapper {

    public static QuestionDto toListAnswer(Question question) {
        QuestionDto qdto = new QuestionDto();
        qdto.setQuestion(question.getQuestion());
        qdto.setAnswers(question.getIncorrectAnswers(), question.getCorrectAnswer());
        return qdto;
    }
}
