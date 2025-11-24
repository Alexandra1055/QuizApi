package com.example.quizapi.service;

import com.example.quizapi.model.Question;

import java.io.IOException;

public interface GameService {

    Question fetchQuestion(String mediaName, String difficulty) throws IOException, InterruptedException;
}
