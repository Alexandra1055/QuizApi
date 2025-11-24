package com.example.quizapi.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "Ranking")
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(name = "total_questions", nullable = false)
    protected int totalQuestions;
    @Column(name = "correct_answers", nullable = false)
    protected int correctAnswers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private long time;

    public Ranking(int totalQuestions, int correctAnswers, User user, long time) {
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.user = user;
        this.time = time;
    }

    public Ranking() {

    }
}
