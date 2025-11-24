package com.example.quizapi.controller;

import com.example.quizapi.service.GameService;
import com.example.quizapi.service.GameServiceImpl;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "gameServlet", value =  "/game")
public class GameServlet extends HttpServlet {
    private GameService service;

    @Override
    public void init(ServletConfig config) throws ServletException  {
        service = new GameServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
