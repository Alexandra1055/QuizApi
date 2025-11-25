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
    @Column(name = "wrong_answers", nullable = false)
    protected int wrongAnswers;
    @Column(name = "correct_answers", nullable = false)
    protected int correctAnswers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private long time;

    public Ranking(int wrongAnswers, int correctAnswers, User user, long time) {
        this.wrongAnswers = wrongAnswers;
        this.correctAnswers = correctAnswers;
        this.user = user;
        this.time = time;
    }

    public Ranking() {

    }
}
