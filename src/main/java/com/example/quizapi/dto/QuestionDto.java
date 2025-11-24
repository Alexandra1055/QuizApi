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



//        {
//        "category": "music",
//        "id": "622a1c397cc59eab6f950bf5",
//        "correctAnswer": "The Doors",
//        "incorrectAnswers": [
//        "Styx",
//        "The Pussycat Dolls",
//        "Three 6 Mafia"
//        ],
//        "question": {
//        "text": "Which American rock band released the song 'Riders on the Storm'?"
//        },
//        "tags": [
//        "music"
//        ],
//        "type": "text_choice",
//        "difficulty": "hard",
//        "regions": [],
//        "isNiche": false
//        }
//        ]
