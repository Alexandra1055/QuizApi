package com.example.quizapi.service;

import com.example.quizapi.dto.QuestionDto;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public interface GameService {

    QuestionDto fetchQuestion(String mediaName, String difficulty, HttpSession session) throws IOException, InterruptedException;
}
