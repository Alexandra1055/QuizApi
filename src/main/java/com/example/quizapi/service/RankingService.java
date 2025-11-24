package com.example.quizapi.service;

import com.example.quizapi.model.Ranking;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface RankingService {

    Ranking saveSessionResults(HttpSession session);
    List<Ranking> getTopRankings();

}
