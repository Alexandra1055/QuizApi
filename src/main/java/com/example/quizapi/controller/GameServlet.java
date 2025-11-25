package com.example.quizapi.controller;

import com.example.quizapi.dto.QuestionDto;
import com.example.quizapi.model.Question;
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
import java.security.Timestamp;
import java.util.Date;

import static com.example.quizapi.model.Difficulty.*;
import static com.example.quizapi.util.Mapper.toListAnswer;

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

        Integer correctAnswerCount = (Integer) session.getAttribute("correctAnswerCount");

        String difficulty = "";

        if( correctAnswerCount <= 3){
            difficulty = easy.name();
        } else if (correctAnswerCount <= 6){
            difficulty = medium.name();
        } else {
            difficulty = hard.name();
        }

        Question question = gameService.fetchQuestion("trivia", difficulty);

        System.out.println(difficulty);

        QuestionDto questionDto = toListAnswer(question);

        session.setAttribute("question", questionDto);

        session.setAttribute("currentQuestion", question.getQuestion());
        System.out.println(question.getQuestion());
        session.setAttribute("correctAnswer", question.getCorrectAnswer());
        System.out.println(question.getCorrectAnswer());
        session.setAttribute("incorrectAnswers", question.getIncorrectAnswers());

        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userAnswer = request.getParameter("answer");
        String correctAnswer = (String) session.getAttribute("correctAnswer");

        Integer remainingTime = (Integer) session.getAttribute("remainingTime");

        if (remainingTime == null) {
            remainingTime = 60;
            session.setAttribute("remainingTime", remainingTime);
        }

        if(remainingTime <= 0 || userAnswer == null){

            Long startTime = (Long) session.getAttribute("startTime");

            if (startTime == null) {
                startTime = System.currentTimeMillis();
                session.setAttribute("startTime", startTime);
            }

            long finishTime = System.currentTimeMillis();

            long totalTimeInMillis = finishTime - startTime;

            System.out.println("startTime in doPost " + startTime);
            System.out.println("endTime in doPost " + finishTime);
            System.out.println("totalTime in doPost " + totalTimeInMillis);

            long totalTime = totalTimeInMillis / 1000;

            System.out.println("total time seconds in doPost " + totalTime);

            session.setAttribute("time", totalTime);

            Ranking ranking = rankingService.saveSessionResults(session);


            if(ranking != null){
                request.setAttribute("ranking", ranking);
            }

            session.invalidate();
            request.getRequestDispatcher("final.jsp").forward(request, response);
        }


        if(userAnswer.equals(correctAnswer)) {
            int timeToAdd = 5;
            Integer correctAnswerInt = (Integer) session.getAttribute("correctAnswerCount");
            session.setAttribute("remainingTime", remainingTime + timeToAdd);
            session.setAttribute("correctAnswerCount",  correctAnswerInt + 1);
        } else {
            int timeToSubstract = 10;
            Integer incorrectAnswerInt = (Integer) session.getAttribute("incorrectAnswersCount");
            session.setAttribute("remainingTime", remainingTime - timeToSubstract);
            session.setAttribute("incorrectAnswersCount", incorrectAnswerInt + 1);
        }

        response.sendRedirect("game");

    }
}
