package com.example.quizapi.dto;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private String correctAnswer;
    private ArrayList<String> incorrectAnswers;
    private String question;
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
