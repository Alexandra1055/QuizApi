package com.example.quizapi.service;

import com.example.quizapi.dao.RankingDao;
import com.example.quizapi.dao.RankingDaoJdbc;
import com.example.quizapi.dao.RankingDaoOrm;
import com.example.quizapi.model.Ranking;
import com.example.quizapi.model.User;

import jakarta.servlet.http.HttpSession;
import java.util.List;


public class RankingServiceImpl implements RankingService {

    private final RankingDao rankingDao;

    public RankingServiceImpl() {
        this.rankingDao = new RankingDaoJdbc();
    }

    @Override
    public Ranking saveSessionResults(HttpSession session) {
        Ranking ranking = new Ranking();

        User user = (User) session.getAttribute("user");

        if (user == null) {
            throw new IllegalStateException("No user found in session");
        }

        ranking.setUser(user);

        Integer correctAnswers = (Integer) session.getAttribute("correctAnswerCount");
        Integer incorrectAnswers = (Integer) session.getAttribute("incorrectAnswersCount");
        Long totalTime = (Long) session.getAttribute("time");

        ranking.setWrongAnswers(incorrectAnswers);
        ranking.setCorrectAnswers(correctAnswers);
        ranking.setTime(totalTime);

        return rankingDao.addRanking(ranking);
    }

    @Override
    public List<Ranking> getTopRankings() {
        return rankingDao.findTop10();
    }
}
