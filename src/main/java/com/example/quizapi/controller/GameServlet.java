package com.example.quizapi.controller;

import com.example.quizapi.dto.QuestionDto;
import com.example.quizapi.model.Ranking;
import com.example.quizapi.service.GameServiceImpl;
import com.example.quizapi.service.RankingService;
import com.example.quizapi.service.RankingServiceImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "gameServlet", value =  "/game")
public class GameServlet extends HttpServlet {
    private GameServiceImpl gameService;
    private RankingService rankingService;

    @Override
    public void init(ServletConfig config) throws ServletException  {
        gameService = new GameServiceImpl();
        rankingService = new RankingServiceImpl();
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer remainingTime = (Integer) session.getAttribute("remainingTime");
        Date timestamp;

        if(remainingTime == null){
            timestamp = new Date();
            remainingTime = 60;
            session.setAttribute("remainingTime", remainingTime);
            session.setAttribute("correctAnswerCount", 0);
            session.setAttribute("incorrectAnswersCount", 0);
            session.setAttribute("startTime", timestamp.getTime());
        }

        request.setAttribute("remainingTime", remainingTime);

        Integer correctAnswerCount = (Integer) session.getAttribute("correctAnswerCount");

        String difficulty = gameService.getDifficulty(correctAnswerCount);

        QuestionDto questionDto = gameService.fetchQuestion("trivia", difficulty, session);

        session.setAttribute("question", questionDto);

        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userAnswer = request.getParameter("answer");
        String correctAnswer = (String) session.getAttribute("correctAnswer");
        Integer remainingTime = Integer.parseInt(request.getParameter("remainingTime"));

        if(remainingTime <= 0){
            handleTimeOut(session, request, response);
            return;
        }

        if(userAnswer == null) {
            request.getRequestDispatcher("game.jsp").forward(request, response);
            return;
        }

        gameService.updateSessionForAnswer(session, userAnswer, correctAnswer, remainingTime);
        response.sendRedirect("game");
    }

    protected void handleTimeOut(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long startTime = (Long) session.getAttribute("startTime");

        if (startTime == null) {
            startTime = System.currentTimeMillis();
            session.setAttribute("startTime", startTime);
        }

        long finishTime = System.currentTimeMillis();
        long totalTimeInMillis = finishTime - startTime;
        long totalTime = totalTimeInMillis / 1000;

        session.setAttribute("time", totalTime);

        Ranking ranking = rankingService.saveSessionResults(session);

        if(ranking != null){
            request.setAttribute("ranking", ranking);
        }

        session.invalidate();
        request.getRequestDispatcher("final.jsp").forward(request, response);
    }
}
