package com.example.quizapi.dao;

import com.example.quizapi.model.Ranking;

import java.util.List;

public interface RankingDao {
    Ranking addRanking(Ranking ranking);
    List<Ranking> findTop10();
}
