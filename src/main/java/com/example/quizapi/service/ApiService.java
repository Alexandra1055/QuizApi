package com.example.quizapi.service;

import com.example.quizapi.dto.QuestionDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

public class ApiService {
    String baseUrlSearch = "https://the-trivia-api.com/v2/questions?difficulties=";

    private final HttpClient httpClient;
    private final Gson gson;

    public ApiService(){
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new GsonBuilder().create();
    }

    public QuestionDto fetchQuestion(String mediaName, String difficulty) throws IOException, InterruptedException {
        URI LIST_ENDPOINT = URI.create(baseUrlSearch + difficulty + "&limit=1");

        HttpRequest request = HttpRequest.newBuilder(LIST_ENDPOINT).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ensureSuccess(response, LIST_ENDPOINT.toString());
        return gson.fromJson(response.body(), QuestionDto.class);
    }

    private void ensureSuccess(HttpResponse <?> response, String url){
        if(response.statusCode() >= 400){
            throw new RuntimeException("The call " + url + " has failed with the code " + response.statusCode());
        }
    }
}
