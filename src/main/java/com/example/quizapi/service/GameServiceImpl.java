package com.example.quizapi.service;

import com.example.quizapi.dto.QuestionDto;
import com.example.quizapi.model.Question;
import com.google.gson.*;
import jakarta.jms.Session;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;

import static com.example.quizapi.model.Difficulty.*;
import static com.example.quizapi.util.Mapper.toListAnswer;

public class GameServiceImpl implements GameService{
    String baseUrlSearch = "https://the-trivia-api.com/v2/questions?difficulties=";

    private final HttpClient httpClient;
    private final Gson gson;

    public GameServiceImpl(){
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new GsonBuilder().create();
    }

    public QuestionDto fetchQuestion(String mediaName, String difficulty, HttpSession session) throws IOException, InterruptedException {
        URI LIST_ENDPOINT = URI.create(baseUrlSearch + difficulty + "&limit=1");

        HttpRequest request = HttpRequest.newBuilder(LIST_ENDPOINT).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ensureSuccess(response, LIST_ENDPOINT.toString());

        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        JsonObject questionObject = jsonArray.get(0).getAsJsonObject();

        String correctAnswer = questionObject.get("correctAnswer").getAsString();
        ArrayList<String> incorrectAnswers = new ArrayList<>();
        for (JsonElement elem : questionObject.getAsJsonArray("incorrectAnswers")) {
            incorrectAnswers.add(elem.getAsString());
        }
        String questionText = questionObject.getAsJsonObject("question").get("text").getAsString();

        Question question = new Question(correctAnswer, incorrectAnswers, questionText);

        session.setAttribute("currentQuestion", question.getQuestion());
        session.setAttribute("correctAnswer", question.getCorrectAnswer());
        session.setAttribute("incorrectAnswers", question.getIncorrectAnswers());

        return toListAnswer(question);
    }

    private void ensureSuccess(HttpResponse <?> response, String url){
        if(response.statusCode() >= 400){
            throw new RuntimeException("The call " + url + " has failed with the code " + response.statusCode());
        }
    }

    public String getDifficulty(int correctAnswerCount){
        if(correctAnswerCount <= 3){
            return easy.name();
        } else if (correctAnswerCount <= 6){
            return medium.name();
        }
        return hard.name();
    }

    public void updateSessionForAnswer(HttpSession session,  String userAnswer, String correctAnswer, Integer remainingTime){
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
    }

}
