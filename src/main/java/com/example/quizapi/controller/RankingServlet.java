package com.example.quizapi.controller;

import com.example.quizapi.model.Ranking;
import com.example.quizapi.service.RankingService;
import com.example.quizapi.service.RankingServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "rankingServlet", value = "/ranking")
public class RankingServlet extends HttpServlet {
    private RankingService rankingService;

    @Override
    public void init() {
        rankingService = new RankingServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Ranking> rankings = rankingService.getTopRankings();
        request.setAttribute("rankings", rankings);
        request.getRequestDispatcher("/ranking.jsp").forward(request, response);
    }
}
