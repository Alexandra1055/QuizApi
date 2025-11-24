package com.example.quizapi.service;

import com.example.quizapi.model.Question;
import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;

public class GameServiceImpl implements GameService{
    String baseUrlSearch = "https://the-trivia-api.com/v2/questions?difficulties=";

    private final HttpClient httpClient;
    private final Gson gson;

    public GameServiceImpl(){
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new GsonBuilder().create();
    }

    public Question fetchQuestion(String mediaName, String difficulty) throws IOException, InterruptedException {
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

        return new Question(correctAnswer, incorrectAnswers, questionText); // Return the populated Question object
    }

    private void ensureSuccess(HttpResponse <?> response, String url){
        if(response.statusCode() >= 400){
            throw new RuntimeException("The call " + url + " has failed with the code " + response.statusCode());
        }
    }


}
